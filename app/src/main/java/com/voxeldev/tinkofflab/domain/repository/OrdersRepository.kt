package com.voxeldev.tinkofflab.domain.repository

import com.voxeldev.tinkofflab.domain.functional.Either
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel

interface OrdersRepository {

    /**
     * Gets all orders
     * @return List of orders or exception
     */
    fun getOrders(): Either<Exception, List<OrderModel>>

    /**
     * Creates new order
     * @param order Order data
     * @return Created order or exception
     */
    fun createOrder(order: OrderModel): Either<Exception, OrderModel>

    /**
     * Updates order
     * @param id Order id
     * @param order Order data
     * @return Updated order or exception
     */
    fun updateOrder(id: Int, order: OrderModel): Either<Exception, OrderModel>
}
