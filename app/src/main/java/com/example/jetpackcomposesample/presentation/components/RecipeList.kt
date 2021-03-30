package com.example.jetpackcomposesample.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcomposesample.R
import com.example.jetpackcomposesample.domain.model.Recipe
import com.example.jetpackcomposesample.presentation.ui.recipelist.PAGE_SIZE
import com.example.jetpackcomposesample.presentation.ui.recipelist.RecipeListEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerNextPage: (RecipeListEvent) -> Unit,
    navController: NavController,
    scaffoldState: ScaffoldState,
    snackbarController: SnackbarController,
){
    Box(modifier = Modifier
        .background(color = MaterialTheme.colors.surface)
    ) {
        if (loading && recipes.isEmpty()) {
            LoadingRecipeListShimmer(imageHeight = 250.dp)
        }
        else if(recipes.isEmpty()){
            NothingHere()
        }
        else {
            LazyColumn{
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->
                    onChangeScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onTriggerNextPage(RecipeListEvent.NextPageEvent)
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            if(recipe.id != null){
                                val bundle = Bundle()
                                bundle.putInt("recipeId", recipe.id)
                                navController.navigate(R.id.viewRecipe, bundle)
                            }
                            else{
                                snackbarController.getScope().launch {
                                    snackbarController.showSnackbar(
                                        scaffoldState = scaffoldState,
                                        message = "Recipe Error",
                                        actionLabel = "OK",
                                    )
                                }
                            }
                        }
                    )
                }
            }
            DefaultSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
