package com.task.airlift_ecommerce_task.ui.fragments.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.task.airlift_ecommerce_task.R
import com.task.airlift_ecommerce_task.databinding.FragmentHomeBinding
import com.task.airlift_ecommerce_task.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding

    /////////////////////////////////////////////////////////////////////
    ///////              Fragment Lifecycle Methods
    /////////////////////////////////////////////////////////////////////

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false)

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