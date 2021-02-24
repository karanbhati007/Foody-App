package com.ksb.foody.viewmodeles

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.ksb.foody.data.DataStoreRepository
import com.ksb.foody.util.Constants.Companion.API_KEY
import com.ksb.foody.util.Constants.Companion.DEFAULT_CUISINE
import com.ksb.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.ksb.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.ksb.foody.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.ksb.foody.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.ksb.foody.util.Constants.Companion.QUERY_API_KEY
import com.ksb.foody.util.Constants.Companion.QUERY_CUISINE
import com.ksb.foody.util.Constants.Companion.QUERY_DIET
import com.ksb.foody.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.ksb.foody.util.Constants.Companion.QUERY_NUMBER
import com.ksb.foody.util.Constants.Companion.QUERY_SEARCH
import com.ksb.foody.util.Constants.Companion.QUERY_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class RecipesViewModel @ViewModelInject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false


    val readMealAndDietType = dataStoreRepository.readMealAndDietType


    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }


    fun applyQueries(): HashMap<String, String> {

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_CUISINE] = DEFAULT_CUISINE
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_CUISINE] = DEFAULT_CUISINE
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun showNetworkStatus(view: View) {
        if (!networkStatus) {
            networkMessage("No Internet Connection.", view)
            backOnline = true
        } else {
            // No need for try and catch, but for safety
            if (backOnline)
                networkMessage("Internet Connection is Back.", view)
        }
    }

    private fun networkMessage(message: String, view: View) {
        try {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT)
                .show()
        }
    }
}