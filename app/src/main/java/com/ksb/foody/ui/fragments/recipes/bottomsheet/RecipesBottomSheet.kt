package com.ksb.foody.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ksb.foody.R
import com.ksb.foody.databinding.RecipesBottomSheetBinding
import com.ksb.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.ksb.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.ksb.foody.viewmodeles.RecipesViewModel
import java.lang.Exception
import java.util.*


class RecipesBottomSheet : BottomSheetDialogFragment() {

    private val TAG = "RecipesBottomSheet"

    private lateinit var binding: RecipesBottomSheetBinding
    private lateinit var recipesViewModel: RecipesViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipesBottomSheetBinding.inflate(inflater, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner, {
            mealTypeChip = it.selectedMealType
            mealTypeChipId = it.selectedMealTypeId
            dietTypeChip = it.selectedDietType
            dietTypeChipId = it.selectedDietTypeId
            updateChip(binding.mealTypeChipGroup, mealTypeChipId)
            updateChip(binding.dietTypeChipGroup, dietTypeChipId)
        })

        binding.mealTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            mealTypeChip = chip.text.toString().toLowerCase(Locale.ROOT)
            mealTypeChipId = selectedChipId
        }

        binding.dietTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            dietTypeChip = chip.text.toString().toLowerCase(Locale.ROOT)
            dietTypeChipId = selectedChipId
        }

        binding.applyButton.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )
            val action = RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateChip(chipGroup: ChipGroup, chipId: Int) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            }catch (e: Exception){
                Log.d(TAG, "updateChip: "+e.message)
            }
        }
    }


}