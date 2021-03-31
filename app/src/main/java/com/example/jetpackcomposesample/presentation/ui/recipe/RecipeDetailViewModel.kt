package com.example.jetpackcomposesample.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposesample.domain.model.Recipe
import com.example.jetpackcomposesample.interactors.recipe.GetRecipeUsecase
import com.example.jetpackcomposesample.presentation.ui.recipe.RecipeEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val STATE_KEY_RECIPE = "state.key.recipeId"

@HiltViewModel
class RecipeDetailViewModel
@Inject
constructor(
    private val getRecipeUsecase: GetRecipeUsecase,
    @Named("auth_token") private val token: String,
    private val state: SavedStateHandle,
) : ViewModel() {

    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    //this will help to happen once or load recipe detail once
    val onLoad: MutableState<Boolean> = mutableStateOf(false)

    init {
        state.get<Int>(STATE_KEY_RECIPE)?.let { recipeId ->
            onTriggerEvent(GetRecipeEvent(recipeId))
        }
    }

    fun onTriggerEvent(event: GetRecipeEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GetRecipeEvent -> {
                        if (recipe.value == null) {
                            getRecipe(event.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("onTriggerEvent", "onTriggerEvent: Exception $e, ${e.cause}")
            }
        }
    }

    private fun getRecipe(id: Int) {
        getRecipeUsecase.execute(id, token).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { data ->
                recipe.value = data
                state.set(STATE_KEY_RECIPE, data.id)
            }

            dataState.error?.let { error ->
                Log.e("getRecipe", "getRecipe: $error")
            }
        }.launchIn(viewModelScope)
    }
}