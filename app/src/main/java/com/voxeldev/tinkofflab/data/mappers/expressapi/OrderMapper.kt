package com.voxeldev.tinkofflab.data.mappers.expressapi

import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.OrderCreateRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.OrderUpdateRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses.OrderResponse
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderMapper @Inject constructor(
    private val addressMapper: AddressMapper,
    private val itemMapper: ItemMapper,
    private val timeSlotMapper: TimeSlotMapper
) {

    @Throws(NullPointerException::class)
    fun toModel(order: OrderResponse): OrderModel =
        OrderModel(
            addressMapper.toModel(order.address!!),
            order.paymentMethod!!,
            timeSlotMapper.toModel(order.deliverySlot!!),
            order.items!!.map { itemMapper.toModel(it) },
            order.comment!!,
            order.status!!,
            order.id!!
        )

    fun toResponse(order: OrderModel): OrderResponse =
        OrderResponse(
            addressMapper.toResponse(order.address),
            order.paymentMethod,
            timeSlotMapper.toResponse(order.deliverySlot),
            order.items.map { itemMapper.toResponse(it) },
            order.comment,
            order.status,
            order.id
        )

    fun toCreateRequest(order: OrderModel): OrderCreateRequest =
        OrderCreateRequest(
            addressMapper.toResponse(order.address),
            order.paymentMethod,
            timeSlotMapper.toResponse(order.deliverySlot),
            order.items.map { itemMapper.toResponse(it) },
            order.comment,
            order.status
        )

    fun toUpdateRequest(order: OrderModel): OrderUpdateRequest =
        OrderUpdateRequest(
            addressMapper.toResponse(order.address),
            order.paymentMethod,
            timeSlotMapper.toResponse(order.deliverySlot),
            order.comment,
            order.status
        )
}
