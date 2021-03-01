package com.ksb.foody.data

import android.util.Log
import com.ksb.foody.data.network.FoodRecipeApi
import com.ksb.foody.model.FoodJoke
import com.ksb.foody.model.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val foodRecipeApi: FoodRecipeApi) {

    private val TAG = "RemoteDataSource"

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return  foodRecipeApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQueries: Map<String,String>): Response<FoodRecipe>{
        return foodRecipeApi.searchRecipes(searchQueries)
    }

    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke>{
        return foodRecipeApi.getFoodJoke(apiKey)
    }
}