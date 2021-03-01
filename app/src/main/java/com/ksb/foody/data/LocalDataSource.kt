package com.ksb.foody.data

import com.ksb.foody.data.room.RecipesDao
import com.ksb.foody.data.room.entities.FavouritesEntity
import com.ksb.foody.data.room.entities.FoodJokeEntity
import com.ksb.foody.data.room.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: RecipesDao) {

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        dao.insertRecipes(recipesEntity)
    }

    fun readRecipes(): Flow<List<RecipesEntity>>{
        return dao.readRecipes()
    }

    fun readFavRecipe():Flow<List<FavouritesEntity>>{
        return dao.readFavRecipe()
    }

    suspend fun insertFavRecipe(favouritesEntity: FavouritesEntity){
        dao.insertFavRecipe(favouritesEntity)
    }

    suspend fun deleteFavRecipe(favouritesEntity: FavouritesEntity){
        dao.deleteFavRecipe(favouritesEntity)
    }

    suspend fun deleteAllFavRecipes(){
        dao.deleteAllFavRecipe()
    }

    fun readFoodJoke():Flow<List<FoodJokeEntity>>{
        return dao.readFoodJoke()
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
        return dao.insertFoodJoke(foodJokeEntity)
    }

}