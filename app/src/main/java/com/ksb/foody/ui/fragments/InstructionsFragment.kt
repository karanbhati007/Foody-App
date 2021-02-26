package com.ksb.foody.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.ksb.foody.R
import com.ksb.foody.model.Result
import com.ksb.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import kotlinx.android.synthetic.main.fragment_instructions.view.*
import java.lang.Exception


class InstructionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_instructions, container, false)

        try {
            val mBundle: Result? = arguments?.getParcelable(RECIPE_RESULT_KEY)
            view.instructions_webview.webViewClient = object : WebViewClient() {}
            view.instructions_webview.loadUrl(mBundle!!.sourceUrl)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return view
    }
}