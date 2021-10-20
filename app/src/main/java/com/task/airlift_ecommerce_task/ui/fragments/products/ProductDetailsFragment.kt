package com.task.airlift_ecommerce_task.ui.fragments.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.task.airlift_ecommerce_task.R
import com.task.airlift_ecommerce_task.data.remote.models.response.ResponseProduct
import com.task.airlift_ecommerce_task.databinding.FragmentHomeBinding
import com.task.airlift_ecommerce_task.databinding.FragmentProductDetailsBinding
import com.task.airlift_ecommerce_task.ui.sharedViewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    private val args: ProductDetailsFragmentArgs by navArgs()

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var product: ResponseProduct

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

        product = args.product

        binding.lifecycleOwner = viewLifecycleOwner
        binding.product = product

        return binding.root
    }

    /////////////////////////////////////////////////////////////////////
    //////////                  UI related methods
    /////////////////////////////////////////////////////////////////////

    private fun setupViews() {

    }

    private fun setupViewsListeners() {
        activity?.let { a ->
            binding.ibnBack.setOnClickListener {
                a.onBackPressed()
            }

            binding.btnAddToCart.setOnClickListener {
                product.cartQuantity?.value = product.cartQuantity?.value?.plus(1)

                mainViewModel.addProductInCart(product)
            }

            binding.ibnAdd.setOnClickListener {
                product.cartQuantity?.value = product.cartQuantity?.value?.plus(1)

                mainViewModel.addProductInCart(product)
            }

            binding.ibnRemove.setOnClickListener {
                product.cartQuantity?.value = product.cartQuantity?.value?.minus(1)

                mainViewModel.removeProductFromCart(product.id)
            }
        }
    }
}