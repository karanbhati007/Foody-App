package com.ksb.foody.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ksb.foody.model.FoodRecipe
import com.ksb.foody.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {

    @PrimaryKey(autoGenerate = false) // made false so that every time new data will be stored when fetched through API
    var id: Int = 0

}