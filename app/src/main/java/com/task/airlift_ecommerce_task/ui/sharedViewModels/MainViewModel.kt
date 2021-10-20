package com.task.airlift_ecommerce_task.ui.sharedViewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.airlift_ecommerce_task.data.DataRepository
import com.task.airlift_ecommerce_task.data.db.entities.Category
import com.task.airlift_ecommerce_task.data.db.entities.Product
import com.task.airlift_ecommerce_task.data.remote.models.response.ResponseProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.ref.WeakReference
import javax.inject.Inject

/*
 * Created by Umer on 17-Feb-21.
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    var dataRepository: DataRepository
) : ViewModel() {
    private val weakContext = WeakReference(context)

    val isNetworkAvailable = MutableLiveData<Boolean>().apply {
        value = false
    }

    /////////////////////////////////////////////////////////////////////
    ///////                 Preferences related Code
    /////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////
    ///////                   Database related Code
    /////////////////////////////////////////////////////////////////////

    fun getCartItemsCount(): LiveData<Int> {
        return dataRepository.database.getCartItemsCount()
    }

    fun getCartItemsTotalPrice(): LiveData<Double> {
        return dataRepository.database.getCartItemsTotalPrice()
    }

    fun getCartProducts(callback: (products: List<Product>) -> Unit) {
        dataRepository.database.getCartProducts(callback)
    }

    fun addProductInCart(responseProduct: ResponseProduct, callback: (() -> Unit)? = null) {
        dataRepository.database.addProductInCart(responseProduct, callback)
    }

    fun addProductInCart(product: Product, callback: (() -> Unit)? = null) {
        dataRepository.database.addProductInCart(product, callback)
    }

    fun removeProductFromCart(id: Int, callback: (() -> Unit)? = null) {
        dataRepository.database.removeProductFromCart(id, callback)
    }

    fun deleteProductFromCart(id: Int, callback: (() -> Unit)? = null) {
        dataRepository.database.deleteProductFromCart(id, callback)
    }

    /////////////////////////////////////////////////////////////////////
    ///////                    Remote related Code
    /////////////////////////////////////////////////////////////////////
}