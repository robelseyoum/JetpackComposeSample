package com.example.jetpackcomposesample.interactors.recipe

import com.example.jetpackcomposesample.domain.data.DataState
import com.example.jetpackcomposesample.domain.model.Recipe
import com.example.jetpackcomposesample.network.RecipeService
import com.example.jetpackcomposesample.network.model.RecipeDtoMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecipeUsecase (
    private val recipeService: RecipeService,
    private val recipeDtoMapper: RecipeDtoMapper,
    ){

    fun execute(
        recipeId: Int,
        token: String
    ): Flow<DataState<Recipe>> = flow {
        try {
            emit(DataState.loading())
            // just to show pagination, since api is fast
            delay(1000)
            // get recipe from network
            val networkRecipe = getRecipeFromNetwork(token, recipeId) // dto -> domain
            emit(DataState.success(networkRecipe))
        }catch (e: Exception){
            emit(DataState.error<Recipe>(e.message?: "Unknown Error"))
        }
    }

    private suspend fun getRecipeFromNetwork(token: String, recipeId: Int): Recipe {
        return recipeDtoMapper.mapToDomainModel(recipeService.get(token, recipeId))
    }
}