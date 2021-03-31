package com.example.jetpackcomposesample.presentation.ui.recipe

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposesample.presentation.components.IMAGE_HEIGHT
import com.example.jetpackcomposesample.presentation.components.LoadingRecipeShimmer
import com.example.jetpackcomposesample.presentation.components.RecipeView
import com.example.jetpackcomposesample.presentation.theme.AppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun RecipeDetailScreen(
    isDarkTheme: Boolean,
    recipeId: Int?,
    viewModel: RecipeDetailViewModel,
) {
    Log.d("RecipeDetailScreen", "RecipeDetailScreen: $viewModel")
    Log.d("RecipeDetailScreen", "RecipeDetailScreen-recipeID: $recipeId")

    if (recipeId == null) {
        TODO("Show Invalid Recipe")
    } else {
        // fire a one-off event to get the recipe from api
        val onLoad = viewModel.onLoad.value
        if (!onLoad) {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }

        val loading = viewModel.loading.value
        val recipe = viewModel.recipe.value
        val scaffoldState = rememberScaffoldState()

        AppTheme(
            displayProgressBar = loading,
            scaffoldState = scaffoldState,
            darkTheme = isDarkTheme
        ) {
            Scaffold(
                scaffoldState = scaffoldState,
                snackbarHost = { scaffoldState.snackbarHostState }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (loading && recipe == null) {
                        LoadingRecipeShimmer(imageHeight = IMAGE_HEIGHT.dp)
                    } else if (!loading && recipe == null && onLoad) {
                        TODO("Invalid Recipe")
                    } else {
                        recipe?.let { RecipeView(recipe = it) }
                    }
                }
            }
        }
    }
}