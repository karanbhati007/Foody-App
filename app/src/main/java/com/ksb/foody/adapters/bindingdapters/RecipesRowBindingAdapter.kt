package com.ksb.foody.adapters.bindingdapters

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.ksb.foody.R
import com.ksb.foody.model.Result
import com.ksb.foody.ui.fragments.recipes.RecipesFragmentArgs
import com.ksb.foody.ui.fragments.recipes.RecipesFragmentDirections
import org.jsoup.Jsoup
import java.lang.Exception

class RecipesRowBindingAdapter {

    companion object {
        private val TAG = "RecipesRowBindingAdapte"

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout: ConstraintLayout,result: Result){
            recipeRowLayout.setOnClickListener {
                try{
                    val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                    recipeRowLayout.findNavController().navigate(action)
                }catch (e: Exception){
                    Log.d(TAG, "onRecipeClickListener: $e")
                }
            }
        }

        @BindingAdapter("setNumberOfLikes") // Need not to be same as the func name
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes") // Need not to be same as the func name
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int) {
            textView.text = minutes.toString()
        }


        @BindingAdapter("setVeganColor")
        @JvmStatic
        fun setVeganColor(view: View, color: Boolean) {

            if (color) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    }
                    is ImageView -> {
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                    }
                }
            }
        }

        @BindingAdapter("loadImageFromURL")
        @JvmStatic
        fun loadImageFromURL(imageView: ImageView,imageUrl: String){
            imageView.load(imageUrl){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView,description: String?){
            if(description!=null){
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }
        }


    }
}