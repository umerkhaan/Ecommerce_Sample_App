package com.task.airlift_ecommerce_task.ui.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.airlift_ecommerce_task.R
import com.task.airlift_ecommerce_task.data.db.entities.Product
import com.task.airlift_ecommerce_task.data.remote.models.response.ResponseProduct
import com.task.airlift_ecommerce_task.databinding.FragmentCartBinding
import com.task.airlift_ecommerce_task.ui.adapters.CartProductsAdapter
import com.task.airlift_ecommerce_task.ui.adapters.ProductsAdapter
import com.task.airlift_ecommerce_task.ui.fragments.products.ProductsFragmentDirections
import com.task.airlift_ecommerce_task.ui.sharedViewModels.MainViewModel
import com.task.airlift_ecommerce_task.utils.misc.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var cartAdapter: CartProductsAdapter

    /////////////////////////////////////////////////////////////////////
    ///////              Fragment Lifecycle Methods
    /////////////////////////////////////////////////////////////////////

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)

        setupViews()
        setupViewsListeners()
        setupLiveDataObservers()

        return binding.root
    }

    /////////////////////////////////////////////////////////////////////
    //////////                  UI related methods
    /////////////////////////////////////////////////////////////////////

    private fun setupViews() {
        activity?.let { a ->
            val linearLayoutManager = LinearLayoutManager(a)
            linearLayoutManager.isItemPrefetchEnabled = false

            cartAdapter = CartProductsAdapter(ArrayList())

            binding.rvCart.apply {
                setHasFixedSize(true)
                itemAnimator = null
                adapter = cartAdapter
                layoutManager = linearLayoutManager
                isNestedScrollingEnabled = false
            }

            mainViewModel.getCartProducts { products ->
                cartAdapter.setData(products)
            }
        }
    }

    private fun setupViewsListeners() {
        activity?.let { a ->
            cartAdapter.setListener(object : CartProductsAdapter.OnItemTouchListener {
                override fun onClick(position: Int, product: Product) {

                }

                override fun onAddToCartClick(position: Int, product: Product) {
                    product.cartQuantity?.value = product.cartQuantity?.value?.plus(1)

                    mainViewModel.addProductInCart(product)
                }

                override fun onRemoveFromCartClick(position: Int, product: Product) {
                    if (product.cartQuantity?.value == 1) {
                        cartAdapter.removeItem(position)
                    }

                    product.cartQuantity?.value = product.cartQuantity?.value?.minus(1)

                    mainViewModel.removeProductFromCart(product.id)
                }

                override fun onDelete(position: Int, product: Product) {
                    cartAdapter.removeItem(position)

                    mainViewModel.deleteProductFromCart(product.id)
                }
            })
        }
    }

    private fun setupLiveDataObservers() {
        if (!mainViewModel.getCartItemsCount().hasActiveObservers()) {
            mainViewModel.getCartItemsCount().observe(viewLifecycleOwner) { count ->
                binding.tvTotalItems.text = String.format(getString(R.string.items_format), count)

                if (count == 0) {
                    binding.tvTotalItems.visibility = GONE
                    binding.lytCheckout.visibility = GONE
                    binding.tvEmptyCart.visibility = VISIBLE
                } else {
                    binding.tvTotalItems.visibility = VISIBLE
                    binding.lytCheckout.visibility = VISIBLE
                    binding.tvEmptyCart.visibility = GONE
                }
            }
        }

        if (!mainViewModel.getCartItemsTotalPrice().hasActiveObservers()) {
            mainViewModel.getCartItemsTotalPrice().observe(viewLifecycleOwner) { price ->
                binding.tvTotalPrice.text =
                    String.format(getString(R.string.total_ammount_format), price)
            }
        }
    }
}