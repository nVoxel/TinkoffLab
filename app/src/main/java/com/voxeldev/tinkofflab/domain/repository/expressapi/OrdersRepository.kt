package com.voxeldev.tinkofflab.domain.repository.expressapi

import com.voxeldev.tinkofflab.utils.functional.Either
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
     * @param order Order data
     * @return Updated order or exception
     */
    fun updateOrder(order: OrderModel): Either<Exception, OrderModel>
}
