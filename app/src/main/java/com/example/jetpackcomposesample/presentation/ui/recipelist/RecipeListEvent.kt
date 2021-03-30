package com.example.jetpackcomposesample.presentation.ui.recipelist

sealed class RecipeListEvent{
    object NewSearchEvent : RecipeListEvent()
    object NextPageEvent : RecipeListEvent()
}
