package com.voxeldev.tinkofflab.data.network.expressapi.datasource

import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.AddressSearchRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.OrderCreateRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.OrderUpdateRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses.AddressResponse
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses.OrderResponse
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses.TimeSlotResponse
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpressService @Inject constructor(
    retrofit: Retrofit
) : ExpressApi {
    private val expressApi by lazy { retrofit.create(ExpressApi::class.java) }

    override fun getOrders(): Call<List<OrderResponse>> =
        expressApi.getOrders()

    override fun createOrder(orderCreateRequest: OrderCreateRequest): Call<OrderResponse> =
        expressApi.createOrder(orderCreateRequest)

    override fun updateOrder(
        orderId: Int,
        orderCreateRequest: OrderUpdateRequest
    ): Call<OrderResponse> =
        expressApi.updateOrder(orderId, orderCreateRequest)

    override fun getAddresses(): Call<List<AddressResponse>> =
        expressApi.getAddresses()

    override fun searchAddress(
        addressSearchRequest: AddressSearchRequest
    ): Call<List<AddressResponse>> =
        expressApi.searchAddress(addressSearchRequest)

    override fun getSlots(date: String): Call<List<TimeSlotResponse>> =
        expressApi.getSlots(date)
}
