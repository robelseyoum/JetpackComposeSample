package com.example.jetpackcomposesample.di

import com.example.jetpackcomposesample.interactors.recipelist.SearchRecipesUsecase
import com.example.jetpackcomposesample.network.RecipeService
import com.example.jetpackcomposesample.network.model.RecipeDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

//usecase is only need exist the lifecycle of each usecase, and as long as the viewmodel is existing
@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideSearchRecipe(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper,
    ): SearchRecipesUsecase {
        return SearchRecipesUsecase(
            recipeService = recipeService,
            dtoMapper = recipeDtoMapper,
        )
    }
}