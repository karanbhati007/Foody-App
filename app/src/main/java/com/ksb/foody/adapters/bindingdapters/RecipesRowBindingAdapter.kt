package com.ksb.foody.adapters.bindingdapters

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.ksb.foody.R

class RecipesRowBindingAdapter {

    companion object {

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

    }
}