package com.private_projects.tenews.ui.details

import android.os.Bundle
import com.private_projects.tenews.databinding.FragmentDetailsBinding
import com.private_projects.tenews.utils.ViewBindingFragment

class DetailsFragment :
    ViewBindingFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    companion object {
        private const val ARG_PARAM1 = ""
        private const val ARG_PARAM2 = ""
        fun newInstance(param1: String, param2: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}