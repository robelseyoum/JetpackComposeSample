package com.example.jetpackcomposesample.presentation.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jetpackcomposesample.R
import com.example.jetpackcomposesample.presentation.components.RecipeCard
import com.example.jetpackcomposesample.presentation.BaseApplication
import com.example.jetpackcomposesample.presentation.components.RecipeList
import com.example.jetpackcomposesample.presentation.components.SearchAppBar
import androidx.navigation.findNavController
import com.example.jetpackcomposesample.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                AppTheme(darkTheme = application.isDark.value) {

                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val loading = viewModel.loading.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val page = viewModel.page.value

                    Column {
                        SearchAppBar(
                            query = query,
                            onQueryChanged = viewModel::onQueryChanged,
                            onExecuteSearch = viewModel::newSearch,
                            selectedCategory = selectedCategory,
                            onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                            onToggleTheme = { application.toggleLightTheme() }
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .background(MaterialTheme.colors.surface)
                        ) {
                            LazyColumn {
                                itemsIndexed(
                                    items = recipes
                                ) { index, recipe ->
                                    RecipeCard(recipe = recipe, onClick = {})
                                }
                            }
                            //circularIndeterminateProgressBar(isDisplayed = loading, 0.3f)
                            RecipeList(
                                loading = loading,
                                recipes = recipes,
                                onChangeScrollPosition = {},
                                page = page,
                                onTriggerNextPage = {  },
                                onNavigateToRecipeDetailScreen = {
                                    val bundle = Bundle()
                                    bundle.putInt("recipeId", it)
                                    findNavController().navigate(R.id.viewRecipe, bundle)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}













