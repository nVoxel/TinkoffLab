package com.voxeldev.tinkofflab.data.mappers.expressapi

import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.OrderCreateRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.OrderUpdateRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.models.OrderApiModel
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
    fun toModel(order: OrderApiModel): OrderModel =
        OrderModel(
            addressMapper.toModel(order.address!!),
            order.paymentMethod!!,
            timeSlotMapper.toModel(order.deliverySlot!!),
            order.items!!.map { itemMapper.toModel(it) },
            order.comment!!,
            order.status!!,
            order.id!!
        )

    fun toApiModel(order: OrderModel): OrderApiModel =
        OrderApiModel(
            addressMapper.toApiModel(order.address),
            order.paymentMethod,
            timeSlotMapper.toApiModel(order.deliverySlot),
            order.items.map { itemMapper.toApiModel(it) },
            order.comment,
            order.status,
            order.id
        )

    fun toCreateRequest(order: OrderModel): OrderCreateRequest =
        OrderCreateRequest(
            addressMapper.toApiModel(order.address),
            order.paymentMethod,
            timeSlotMapper.toApiModel(order.deliverySlot),
            order.items.map { itemMapper.toApiModel(it) },
            order.comment,
            order.status
        )

    fun toUpdateRequest(order: OrderModel): OrderUpdateRequest =
        OrderUpdateRequest(
            addressMapper.toApiModel(order.address),
            order.paymentMethod,
            timeSlotMapper.toApiModel(order.deliverySlot),
            order.comment,
            order.status
        )
}
