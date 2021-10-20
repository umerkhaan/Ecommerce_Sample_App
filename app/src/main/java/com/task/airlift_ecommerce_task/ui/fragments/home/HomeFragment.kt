package com.task.airlift_ecommerce_task.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.airlift_ecommerce_task.R
import com.task.airlift_ecommerce_task.data.db.entities.Category
import com.task.airlift_ecommerce_task.data.remote.models.response.ResponseProduct
import com.task.airlift_ecommerce_task.databinding.FragmentHomeBinding
import com.task.airlift_ecommerce_task.ui.adapters.CategoriesAdapter
import com.task.airlift_ecommerce_task.ui.adapters.ProductsAdapter
import com.task.airlift_ecommerce_task.ui.sharedViewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val mainViewModel: MainViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var productsAdapter: ProductsAdapter

    /////////////////////////////////////////////////////////////////////
    ///////              Fragment Lifecycle Methods
    /////////////////////////////////////////////////////////////////////

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var bindingAvailable = false

        if (!::binding.isInitialized
            || categoriesAdapter.itemCount == 0
            || productsAdapter.itemCount == 0
        ) {
            binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        } else {
            bindingAvailable = true
        }

        setupViews(bindingAvailable)
        setupViewsListeners()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = homeViewModel

        setupLiveDataObservers()

        return binding.root
    }

    /////////////////////////////////////////////////////////////////////
    //////////                  UI related methods
    /////////////////////////////////////////////////////////////////////

    private fun setupViews(bindingAvailable: Boolean) {
        activity?.let { a ->
            var linearLayoutManager: LinearLayoutManager

            if (!bindingAvailable) {
                // Setup Categories

                binding.lytShimmerCategories.visibility = VISIBLE

                linearLayoutManager = LinearLayoutManager(a, LinearLayoutManager.HORIZONTAL, false)
                linearLayoutManager.isItemPrefetchEnabled = false
                categoriesAdapter = CategoriesAdapter(ArrayList())

                binding.rvCategories.apply {
                    setHasFixedSize(true)
                    itemAnimator = null
                    adapter = categoriesAdapter
                    layoutManager = linearLayoutManager
                    isNestedScrollingEnabled = false
                }
            }

            // Setup Recommended
            binding.lytShimmerProducts.visibility = VISIBLE

            linearLayoutManager = LinearLayoutManager(a, LinearLayoutManager.HORIZONTAL, false)
            linearLayoutManager.isItemPrefetchEnabled = false

            productsAdapter = ProductsAdapter(ArrayList())

            binding.rvProducts.apply {
                setHasFixedSize(true)
                itemAnimator = null
                adapter = productsAdapter
                layoutManager = linearLayoutManager
                isNestedScrollingEnabled = false
            }
        }
    }

    private fun setupViewsListeners() {
        activity?.let { a ->
            binding.btnViewAllCategories.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCategoriesFragment()
                findNavController().navigate(action)
            }

            categoriesAdapter.setListener(object : CategoriesAdapter.OnItemTouchListener {
                override fun onClick(position: Int, category: Category) {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToProductsFragment(
                            category.title
                        )
                    findNavController().navigate(action)
                }
            })

            productsAdapter.setListener(object : ProductsAdapter.OnItemTouchListener {
                override fun onClick(position: Int, product: ResponseProduct) {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(
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

    private fun setupLiveDataObservers() {
        if (!mainViewModel.isNetworkAvailable.hasActiveObservers()) {
            mainViewModel.isNetworkAvailable.observe(viewLifecycleOwner) { isNetworkAvailable ->
                if (isNetworkAvailable) {
                    homeViewModel.getAllCategories(onResponse = { categories ->
                        categories?.let {
                            categoriesAdapter.setData(it)
                            binding.lytShimmerCategories.visibility = GONE
                        }
                    })

                    homeViewModel.getRecommendedProducts(onResponse = { products ->
                        products?.let {
                            productsAdapter.setData(it)
                            binding.lytShimmerProducts.visibility = GONE
                        }
                    })
                }
            }
        }

        if (!homeViewModel.refreshing.hasActiveObservers()) {
            homeViewModel.refreshing.observe(viewLifecycleOwner) { refreshing ->
                if (refreshing) {
                    homeViewModel.getAllCategories(onResponse = { categories ->
                        categories?.let {
                            categoriesAdapter.setData(it)
                        }
                    })

                    homeViewModel.getRecommendedProducts(onResponse = { products ->
                        products?.let {
                            productsAdapter.setData(it)

                            homeViewModel.refreshing.value = false
                        }
                    })
                }
            }
        }
    }
}