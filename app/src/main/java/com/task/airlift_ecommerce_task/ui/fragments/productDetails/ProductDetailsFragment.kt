package com.task.airlift_ecommerce_task.ui.fragments.productDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.task.airlift_ecommerce_task.R
import com.task.airlift_ecommerce_task.databinding.FragmentHomeBinding
import com.task.airlift_ecommerce_task.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding

    /////////////////////////////////////////////////////////////////////
    ///////              Fragment Lifecycle Methods
    /////////////////////////////////////////////////////////////////////

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)

        setupViews()
        setupViewsListeners()

        return binding.root
    }

    /////////////////////////////////////////////////////////////////////
    //////////                  UI related methods
    /////////////////////////////////////////////////////////////////////

    private fun setupViews() {

    }

    private fun setupViewsListeners() {

    }
}