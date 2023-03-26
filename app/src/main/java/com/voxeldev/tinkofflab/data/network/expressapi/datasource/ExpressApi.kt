package com.voxeldev.tinkofflab.data.network.expressapi.datasource

import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.AddressSearchRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.OrderCreateRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.OrderUpdateRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses.AddressResponse
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses.OrderResponse
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses.TimeSlotResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ExpressApi {

    @GET("orders")
    fun getOrders() : Call<List<OrderResponse>>

    @POST("orders")
    fun createOrder(
        @Body orderCreateRequest: OrderCreateRequest
    ) : Call<OrderResponse>

    @PUT("orders/{order_id}")
    fun updateOrder(
        @Path("order_id") orderId: Int,
        @Body orderCreateRequest: OrderUpdateRequest
    ) : Call<OrderResponse>

    @GET("addresses/my")
    fun getAddresses(): Call<List<AddressResponse>>

    @POST("addresses/search")
    fun searchAddress(
        @Body addressSearchRequest: AddressSearchRequest
    ): Call<List<AddressResponse>>

    @GET("slots")
    fun getSlots(
        @Query("date") date: String
    ): Call<List<TimeSlotResponse>>
}
