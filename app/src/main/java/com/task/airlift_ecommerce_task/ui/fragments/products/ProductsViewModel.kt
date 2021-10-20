package com.task.airlift_ecommerce_task.ui.fragments.products

import android.content.Context
import androidx.lifecycle.ViewModel
import com.task.airlift_ecommerce_task.data.DataRepository
import com.task.airlift_ecommerce_task.data.db.entities.Category
import com.task.airlift_ecommerce_task.data.remote.models.response.ResponseProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    @ApplicationContext context: Context,
    val dataRepository: DataRepository
) : ViewModel() {
    private val weakContext = WeakReference(context)

    /////////////////////////////////////////////////////////////////////
    ///////                 View setup related Code
    /////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////
    ///////                 Preferences related Code
    /////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////
    ///////                  Database related Code
    /////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////
    ///////                   Remote related Code
    /////////////////////////////////////////////////////////////////////

    fun getProductsByCategory(
        category: String,
        onResponse: (products: List<ResponseProduct>?) -> Unit,
        onFailure: ((error: String?) -> Unit)? = null
    ) {
        dataRepository.remote.getProductsByCategory(category, onResponse = { response ->
            response?.let {
                dataRepository.database.getCartItems { cartItems ->
                    val cartProduct = cartItems.map { item -> item.productId }
                    val cartProductQuantity = cartItems.map { item -> item.quantity }

                    it.forEach { product ->
                        if (cartProduct.contains(product.id)) {
                            product.cartQuantity?.value =
                                cartProductQuantity[cartProduct.indexOf(product.id)]
                        }
                    }
                }

                onResponse(response)
            } ?: run {
                onResponse(null)
            }
        }, onFailure = onFailure)
    }
}