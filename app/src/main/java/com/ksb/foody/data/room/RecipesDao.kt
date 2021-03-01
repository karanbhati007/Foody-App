package com.ksb.foody.data.room

import androidx.room.*
import com.ksb.foody.data.room.entities.FavouritesEntity
import com.ksb.foody.data.room.entities.FoodJokeEntity
import com.ksb.foody.data.room.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavRecipe(favouritesEntity: FavouritesEntity)

    @Query("SELECT * FROM favourites_recipes_table ORDER BY id ASC")
    fun readFavRecipe():Flow<List<FavouritesEntity>>

    @Delete
    suspend fun deleteFavRecipe(favouritesEntity: FavouritesEntity)

    @Query("DELETE FROM favourites_recipes_table")
    suspend fun deleteAllFavRecipe()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM food_joke_table")
    fun readFoodJoke():Flow<List<FoodJokeEntity>>

}