package com.task.airlift_ecommerce_task.ui.fragments.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.task.airlift_ecommerce_task.R
import com.task.airlift_ecommerce_task.data.remote.models.response.ResponseProduct
import com.task.airlift_ecommerce_task.databinding.FragmentProductsBinding
import com.task.airlift_ecommerce_task.ui.adapters.ProductsAdapter
import com.task.airlift_ecommerce_task.ui.sharedViewModels.MainViewModel
import com.task.airlift_ecommerce_task.utils.SingleToastUtil
import com.task.airlift_ecommerce_task.utils.misc.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    private val args: ProductsFragmentArgs by navArgs()

    private val mainViewModel: MainViewModel by activityViewModels()
    private val productsViewModel: ProductsViewModel by viewModels()

    private lateinit var productsAdapter: ProductsAdapter

    private lateinit var category: String

    /////////////////////////////////////////////////////////////////////
    ///////              Fragment Lifecycle Methods
    /////////////////////////////////////////////////////////////////////

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized
            || productsAdapter.itemCount == 0
        ) {
            binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false)

            setupViews()
            setupViewsListeners()
        }

        return binding.root
    }

    /////////////////////////////////////////////////////////////////////
    //////////                  UI related methods
    /////////////////////////////////////////////////////////////////////

    private fun setupViews() {
        activity?.let { a ->
            category = args.category

            binding.tvTitle.text = category.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }

            binding.lytShimmerProducts.visibility = VISIBLE

            val gridLayoutManager = GridLayoutManager(a, Constants.GRID_COLUMNS)
            gridLayoutManager.isItemPrefetchEnabled = false

            productsAdapter = ProductsAdapter(ArrayList(), true)

            binding.rvProducts.apply {
                setHasFixedSize(true)
                itemAnimator = null
                adapter = productsAdapter
                layoutManager = gridLayoutManager
                isNestedScrollingEnabled = false
            }

            productsViewModel.getProductsByCategory(category, onResponse = { products ->
                products?.let {
                    productsAdapter.setData(it)
                    binding.lytShimmerProducts.visibility = GONE
                }
            }, onFailure = {
                SingleToastUtil.show(
                    a,
                    String.format(getString(R.string.something_went_wrong_loading), "products"),
                    Toast.LENGTH_LONG
                )
            })
        }
    }

    private fun setupViewsListeners() {
        activity?.let { a ->
            productsAdapter.setListener(object : ProductsAdapter.OnItemTouchListener {
                override fun onClick(position: Int, product: ResponseProduct) {
                    val action =
                        ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment(
                            product
                        )
                    findNavController().navigate(action)
                }

                override fun onAddToCartClick(position: Int, product: ResponseProduct) {
                    product.cartQuantity?.value = product.cartQuantity?.value?.plus(1)

                    mainViewModel.addProductInCart(product)
                }

                override fun onRemoveFromCartClick(position: Int, product: ResponseProduct) {
                    product.cartQuantity?.value = product.cartQuantity?.value?.minus(1)

                    mainViewModel.removeProductFromCart(product.id)
                }
            })
        }
    }
}