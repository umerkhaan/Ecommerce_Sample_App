package com.task.airlift_ecommerce_task.data.remote

import com.task.airlift_ecommerce_task.data.remote.models.request.RequestLogin
import com.task.airlift_ecommerce_task.data.remote.models.response.ResponseLogin
import com.task.airlift_ecommerce_task.data.remote.models.response.ResponseProduct
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IBackendApi {
    @POST("auth/login")
    fun login(
        @Body requestLogin: RequestLogin
    ): Deferred<ResponseLogin?>

    @GET("products/categories")
    fun getAllCategories(): Deferred<List<String>?>

    @GET("products/products")
    fun getAllProducts(): Deferred<List<ResponseProduct>?>

    @GET("products/products/{category}")
    fun getProductsByCategory(
        @Path("category")
        category: String
    ): Deferred<List<ResponseProduct>?>
}