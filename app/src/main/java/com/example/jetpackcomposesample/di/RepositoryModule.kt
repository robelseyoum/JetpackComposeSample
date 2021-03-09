package com.example.jetpackcomposesample.di

import com.example.jetpackcomposesample.network.RecipeService
import com.example.jetpackcomposesample.network.model.RecipeDtoMapper
import com.example.jetpackcomposesample.repository.RecipeRepository
import com.example.jetpackcomposesample.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeMapper: RecipeDtoMapper,
    ): RecipeRepository {
        return RecipeRepositoryImpl(
            recipeService = recipeService,
            mapper = recipeMapper
        )
    }
}
