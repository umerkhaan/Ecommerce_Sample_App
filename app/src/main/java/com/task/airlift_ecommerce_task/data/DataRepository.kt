package com.task.airlift_ecommerce_task.data

import android.content.Context
import com.task.airlift_ecommerce_task.data.db.AppDatabase
import com.task.airlift_ecommerce_task.data.db.entities.CartItem
import com.task.airlift_ecommerce_task.data.db.entities.Category
import com.task.airlift_ecommerce_task.data.db.entities.Product
import com.task.airlift_ecommerce_task.data.preferences.AppPreferences
import com.task.airlift_ecommerce_task.data.remote.IBackendApi
import com.task.airlift_ecommerce_task.data.remote.models.request.RequestLogin
import com.task.airlift_ecommerce_task.data.remote.models.response.ResponseLogin
import com.task.airlift_ecommerce_task.data.remote.models.response.ResponseProduct
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject

class DataRepository @Inject constructor(
    @ApplicationContext context: Context,
    appPreferences: AppPreferences,
    appDatabase: AppDatabase,
    backendApi: IBackendApi
) {
    val weakReference = WeakReference(context)

    val preferences = Preferences(appPreferences)
    val database = Database(appDatabase)
    val remote = Remote(backendApi)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    /////////////////////////////////////////////////////////////////////
    ///////                  Preferences related Code
    /////////////////////////////////////////////////////////////////////

    inner class Preferences(
        private val appPreferences: AppPreferences
    ) {
        // Token related Methods
        fun setToken(token: String) {
            appPreferences.setToken(token)
        }

        fun getToken(): String {
            return appPreferences.getToken()
        }
    }

    /////////////////////////////////////////////////////////////////////
    ///////                 Database related Code
    /////////////////////////////////////////////////////////////////////

    inner class Database(private var appDatabase: AppDatabase) {
        // Category related methods
        fun insertCategories(categories: List<String>, callback: (() -> Unit)? = null) {
            coroutineScope.launch {
                categories.forEach {
                    val category = Category(
                        title = it,
                        image = null
                    )

                    appDatabase.getCategoryDao().insert(category)
                }

                launch(Dispatchers.Main) {
                    callback?.let {
                        it()
                    }
                }
            }
        }

        fun getAllCategories(callback: (categories: List<Category>) -> Unit) {
            coroutineScope.launch {
                val categories = appDatabase.getCategoryDao().getAllCategories()

                launch(Dispatchers.Main) {
                    callback(categories)
                }
            }
        }

        // Product related methods
        fun getProductById(id: Int, callback: (product: Product) -> Unit) {
            coroutineScope.launch {
                val product = appDatabase.getProductDao().getProductById(id)

                launch(Dispatchers.Main) {
                    callback(product)
                }
            }
        }

        // Cart related methods
        fun getCartItemsCount(callback: (count: Int) -> Unit) {
            coroutineScope.launch {
                val count = appDatabase.getCartItemDao().getCartItemsCount()

                launch(Dispatchers.Main) {
                    callback(count)
                }
            }
        }

        fun getCartItems(callback: (cartItems: List<CartItem>) -> Unit) {
            coroutineScope.launch {
                val cartItems = appDatabase.getCartItemDao().getAllItems()

                launch(Dispatchers.Main) {
                    callback(cartItems)
                }
            }
        }

        fun addProductInCart(responseProduct: ResponseProduct, callback: (() -> Unit)? = null) {
            coroutineScope.launch {
                val exists = appDatabase.getProductDao().checkIfProductExist(responseProduct.id)
                exists?.let { productId ->
                    val cartItem = appDatabase.getCartItemDao().getCartItemByProductId(productId)
                    cartItem?.apply {
                        quantity++

                        appDatabase.getCartItemDao().insert(this)
                    } ?: run {
                        appDatabase.getCartItemDao().insert(
                            CartItem(
                                productId = productId,
                                quantity = 1,
                                dateTime = Date()
                            )
                        )
                    }
                } ?: run {
                    val product = responseProduct.toProduct()

                    appDatabase.getProductDao().insert(product)
                    appDatabase.getCartItemDao().insert(
                        CartItem(
                            productId = responseProduct.id,
                            quantity = 1,
                            dateTime = Date()
                        )
                    )
                }

                launch(Dispatchers.Main) {
                    callback?.let {
                        it()
                    }
                }
            }
        }

        fun removeProductFromCart(id: Int, callback: (() -> Unit)? = null) {
            coroutineScope.launch {
                appDatabase.getCartItemDao().removeProductFromCart(id)
                appDatabase.getProductDao().removeProduct(id)

                launch(Dispatchers.Main) {
                    callback?.let {
                        it()
                    }
                }
            }
        }

        fun clearCart(callback: (() -> Unit)? = null) {
            coroutineScope.launch {
                appDatabase.getCartItemDao().deleteAllRecords()

                launch(Dispatchers.Main) {
                    callback?.let {
                        it()
                    }
                }
            }
        }
    }

    /////////////////////////////////////////////////////////////////////
    ///////              Remote Network related Code
    /////////////////////////////////////////////////////////////////////

    inner class Remote(private var backendApi: IBackendApi) {
        fun login(
            userName: String,
            password: String,
            onResponse: (responseLogin: ResponseLogin?) -> Unit
        ) {
            val requestLogin = RequestLogin(userName, password)

            coroutineScope.launch {
                try {
                    val response = backendApi.login(requestLogin).await()

                    launch(Dispatchers.Main) {
                        onResponse(response)
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()

                    launch(Dispatchers.Main) {
                        onResponse(null)
                    }
                }
            }
        }

        fun getAllCategories(onResponse: (categories: List<String>?) -> Unit) {
            coroutineScope.launch {
                try {
                    val response = backendApi.getAllCategories().await()

                    launch(Dispatchers.Main) {
                        onResponse(response)
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()

                    launch(Dispatchers.Main) {
                        onResponse(null)
                    }
                }
            }
        }

        fun getAllProducts(onResponse: (products: List<ResponseProduct>?) -> Unit) {
            coroutineScope.launch {
                try {
                    val response = backendApi.getAllProducts().await()

                    launch(Dispatchers.Main) {
                        onResponse(response)
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()

                    launch(Dispatchers.Main) {
                        onResponse(null)
                    }
                }
            }
        }

        fun getProductsByCategory(
            category: String,
            onResponse: (products: List<ResponseProduct>?) -> Unit
        ) {
            coroutineScope.launch {
                try {
                    val response = backendApi.getProductsByCategory(category).await()

                    launch(Dispatchers.Main) {
                        onResponse(response)
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()

                    launch(Dispatchers.Main) {
                        onResponse(null)
                    }
                }
            }
        }
    }

    /////////////////////////////////////////////////////////////////////
    ///////                     Other Methods
    /////////////////////////////////////////////////////////////////////

    fun cancelAllCoroutines() {
        coroutineScope.cancel()
    }
}