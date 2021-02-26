package com.ksb.foody.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ksb.foody.R
import com.ksb.foody.adapters.IngredientsAdapter
import com.ksb.foody.model.Result
import com.ksb.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import kotlinx.android.synthetic.main.fragment_ingredients.view.*
import kotlinx.android.synthetic.main.fragment_recipes.view.*


class IngredientsFragment : Fragment() {

    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_ingredients, container, false)

        val myBundle: Result? = arguments?.getParcelable(RECIPE_RESULT_KEY)

        setUpRecyclerView(view)
        myBundle?.extendedIngredients?.let { mAdapter.setData(it) }


        return view
    }

    private fun setUpRecyclerView(view:View){
        view.ingredients_recycler_view.adapter = mAdapter
        view.ingredients_recycler_view.layoutManager = LinearLayoutManager(requireContext())
    }
}