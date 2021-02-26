package com.ksb.foody.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ksb.foody.data.room.entities.FavouritesEntity
import com.ksb.foody.data.room.entities.RecipesEntity
import com.ksb.foody.data.room.typeconverter.RecipesTypeConverter

@Database(
    entities = [RecipesEntity::class,FavouritesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao():RecipesDao

}