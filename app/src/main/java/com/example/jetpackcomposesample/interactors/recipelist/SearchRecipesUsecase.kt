package com.example.jetpackcomposesample.interactors.recipelist

import com.example.jetpackcomposesample.domain.data.DataState
import com.example.jetpackcomposesample.domain.model.Recipe
import com.example.jetpackcomposesample.network.RecipeService
import com.example.jetpackcomposesample.network.model.RecipeDtoMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRecipesUsecase(
    private val recipeService: RecipeService,
    private val dtoMapper: RecipeDtoMapper,
) {

    fun execute(
        token: String,
        page: Int,
        query: String
    ): Flow<DataState<List<Recipe>>> = flow {
        try {
            emit(DataState.loading())
            // just to show pagination, api is fast
            delay(1000)

            val recipeList = getRecipesFromNetwork(token = token, page = page, query = query)

            emit(DataState.success(recipeList))

        } catch (e: Exception) {
            emit(DataState.error<List<Recipe>>(e.message ?: "Unknown Error"))
        }
    }


    // WARNING: This will throw exception if there is no network connection
    private suspend fun getRecipesFromNetwork(
        token: String,
        page: Int,
        query: String
    ): List<Recipe> {
        return dtoMapper.toDomainList(
            recipeService.search(
                token = token,
                page = page,
                query = query,
            ).recipes
        )
    }

}