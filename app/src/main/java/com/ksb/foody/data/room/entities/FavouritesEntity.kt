package com.ksb.foody.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ksb.foody.model.Result
import com.ksb.foody.util.Constants.Companion.FAVOURITES_RECIPE_TABLE

@Entity(tableName = FAVOURITES_RECIPE_TABLE)
class FavouritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)