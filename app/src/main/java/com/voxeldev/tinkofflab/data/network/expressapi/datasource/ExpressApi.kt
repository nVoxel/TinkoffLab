package com.voxeldev.tinkofflab.data.network.expressapi.datasource

import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.AddressSearchRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.OrderCreateRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.OrderUpdateRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.models.AddressApiModel
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.models.OrderApiModel
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.models.TimeSlotApiModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ExpressApi {

    @GET("orders")
    fun getOrders() : Call<List<OrderApiModel>>

    @POST("orders")
    fun createOrder(
        @Body orderCreateRequest: OrderCreateRequest
    ) : Call<OrderApiModel>

    @PUT("orders/{order_id}")
    fun updateOrder(
        @Path("order_id") orderId: Int,
        @Body orderCreateRequest: OrderUpdateRequest
    ) : Call<OrderApiModel>

    @GET("addresses/my")
    fun getAddresses(): Call<List<AddressApiModel>>

    @POST("addresses/search")
    fun searchAddress(
        @Body addressSearchRequest: AddressSearchRequest
    ): Call<List<AddressApiModel>>

    @GET("slots")
    fun getSlots(
        @Query("d") date: String
    ): Call<List<TimeSlotApiModel>>
}
