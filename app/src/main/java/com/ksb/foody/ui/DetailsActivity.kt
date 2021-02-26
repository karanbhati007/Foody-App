package com.ksb.foody.ui

import android.os.Bundle
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.ksb.foody.R
import com.ksb.foody.adapters.PagerAdapter
import com.ksb.foody.data.room.entities.FavouritesEntity
import com.ksb.foody.ui.fragments.IngredientsFragment
import com.ksb.foody.ui.fragments.InstructionsFragment
import com.ksb.foody.ui.fragments.OverviewFragment
import com.ksb.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import com.ksb.foody.viewmodeles.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import java.lang.Exception

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels() // lazy initialize

    private var recipeSaved = false
    private var saveRecipeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val adapter = PagerAdapter(resultBundle, fragments, titles, supportFragmentManager)

        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_fav_menu)
        val item = menuItem!!
        changeMenuItemColor(item, R.color.white)
        checkSavedRecipes(item)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_fav_menu) {
            if (!recipeSaved)
                saveToFavourites(item)
            else
                removeFromFav(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavourites(item: MenuItem) {
        val favouritesEntity = FavouritesEntity(0, args.result)
        mainViewModel.insertFavRecipe(favouritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe Saved.")
        recipeSaved = true
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(detailsLayout, message, Snackbar.LENGTH_SHORT).setAction("Okay") {}
            .show()
    }


    private fun removeFromFav(item: MenuItem) {
        val favouritesEntity = FavouritesEntity(saveRecipeId, args.result)
        mainViewModel.deleteFavRecipe(favouritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from Favourites")
        recipeSaved = false
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }


    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavRecipes.observe(this, { favouritesRecipe ->
            try {
                for (savedRecipe in favouritesRecipe) {
                    if (savedRecipe.result.id == args.result.id) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        recipeSaved = true
                        saveRecipeId = savedRecipe.id
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }
}