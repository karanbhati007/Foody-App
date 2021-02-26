package com.ksb.foody.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ksb.foody.R
import com.ksb.foody.databinding.FragmentOverviewBinding
import com.ksb.foody.model.Result
import com.ksb.foody.util.Constants.Companion.RECIPE_RESULT_KEY


class OverviewFragment : Fragment() {

    lateinit var binding:FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOverviewBinding.inflate(inflater,container,false)

        val args = arguments
        val myBundle: Result? = arguments?.getParcelable(RECIPE_RESULT_KEY)

        if(myBundle!=null){
            binding.result = myBundle
        }
        return binding.root
    }

}