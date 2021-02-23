package com.ksb.foody.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksb.foody.viewmodeles.MainViewModel
import com.ksb.foody.R
import com.ksb.foody.adapters.RecipesAdapter
import com.ksb.foody.databinding.FragmentRecipesBinding
import com.ksb.foody.util.Constants
import com.ksb.foody.util.NetworkResult
import com.ksb.foody.util.observeOnce
import com.ksb.foody.viewmodeles.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private val TAG = "RecipesFragment"

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipeViewModel: RecipesViewModel

    private val args by navArgs<RecipesFragmentArgs>()
   // private lateinit var binding get() =
    private val mAdapter by lazy { RecipesAdapter() }  //TODO Check out the use of Lazy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipeViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater,container,false)
        // _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipes, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setUpRecyclerView()
        readDataBase()

        binding.recipesFab.setOnClickListener{
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
        }

        return binding.root
    }

    private fun readDataBase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d(TAG, "readDataBase: ")
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                }else{
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData() {
        Log.d(TAG, "requestApiData: ")
        mainViewModel.getRecipes(recipeViewModel.applyQueries())

        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                   // loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner,{database->
                mAdapter.setData(database[0].foodRecipe)
            })
        }
    }

//https://api.spoonacular.com/recipes/complexSearch?apiKey=00315fb0689f48c3a510fd04cbee6c6c&number=50&type=snack&diet=vegan&addRecipeInformation=true&fillIngredients=true


    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.recyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerView.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}