package com.example.jetpackcomposesample.presentation.ui.recipelist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.jetpackcomposesample.domain.model.Recipe
import com.example.jetpackcomposesample.interactors.recipelist.SearchRecipesUsecase
import com.example.jetpackcomposesample.presentation.ui.recipelist.RecipeListEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named


const val PAGE_SIZE = 30

@ExperimentalCoroutinesApi
@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val searchRecipesUsecase: SearchRecipesUsecase,
    @Named("auth_token") private val token: String,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(ArrayList())

    val query = mutableStateOf("")

    val loading = mutableStateOf(false)

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    val page = mutableStateOf(1)

    var recipeListScrollPosition = 0

    init {
        onTriggerEvent(NewSearchEvent)
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is NewSearchEvent -> {
                        newSearch()
                    }
                    is NextPageEvent -> {
                        nextPage()
                    }
                }
            } catch (e: Exception) {
                Log.e("onTriggerEvent", "onTriggerEvent: Exception: $e")
            }
        }
    }

    private fun newSearch() {
        Log.d("newSearch", "newSearch: query: ${query.value}, page: ${page.value}")
        // New search. Reset the state
        resetSearchState()

        searchRecipesUsecase
            .execute(token = token, page = page.value, query = query.value)
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { list ->
                    recipes.value = list
                }

                dataState.error?.let { error ->
                    Log.e("newSearch", "newSearch: $error")
                }

            }.launchIn(viewModelScope)
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    private suspend fun nextPage() {
        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            incrementPage()
            Log.d("nextPage", "nextPage: triggered: ${page.value}")
            delay(1000)//should be removed
            if (page.value > 1) {
                searchRecipesUsecase
                    .execute(token = token, page = page.value, query = query.value)
                    .onEach { dataState ->
                        loading.value = dataState.loading

                        dataState.data?.let { list ->
                            appendRecipes(list)
                        }

                        dataState.error?.let { error ->
                            Log.e("nextPage", "nextPage error: $error")
                        }

                    }.launchIn(viewModelScope)
            }
        }
    }

    /**
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }


    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }


}

