package com.ksb.foody.data

import com.ksb.foody.data.room.RecipesDao
import com.ksb.foody.data.room.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: RecipesDao) {

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        dao.insertRecipes(recipesEntity)
    }

    fun readDataBase(): Flow<List<RecipesEntity>>{
        return dao.readRecipes()
    }

}