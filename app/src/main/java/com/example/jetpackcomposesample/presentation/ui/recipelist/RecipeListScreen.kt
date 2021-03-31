package com.example.jetpackcomposesample.presentation.ui.recipelist

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.findNavController
import com.example.jetpackcomposesample.presentation.components.RecipeList
import com.example.jetpackcomposesample.presentation.components.SearchAppBar
import com.example.jetpackcomposesample.presentation.theme.AppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun RecipeListScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onNavigateToRecipeDetailScreen: (String) -> Unit,
    viewModel: RecipeListViewModel,
) {
    Log.d("RecipeListScreen", "RecipeListScreen: $viewModel")

    val recipes = viewModel.recipes.value
    val query = viewModel.query.value
    val loading = viewModel.loading.value
    val selectedCategory = viewModel.selectedCategory.value
    val page = viewModel.page.value
    val scaffoldState = rememberScaffoldState()

    AppTheme(
        displayProgressBar = loading,
        scaffoldState = scaffoldState,
        darkTheme = isDarkTheme
    ) {
        Scaffold(
            topBar = {
                SearchAppBar(
                    query = query,
                    onQueryChanged = viewModel::onQueryChanged,
                    onExecuteSearch = { viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent) },
                    selectedCategory = selectedCategory,
                    onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                    onToggleTheme = onToggleTheme
                )
            },
            scaffoldState = scaffoldState,
            snackbarHost = { scaffoldState.snackbarHostState }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
            ) {
                RecipeList(
                    loading = loading,
                    recipes = recipes,
                    onChangeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                    page = page,
                    onTriggerNextPage = { viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent) },
                    onNavigateToRecipeDetailScreen = onNavigateToRecipeDetailScreen
                )
            }
        }
    }
}