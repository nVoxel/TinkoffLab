package com.voxeldev.tinkofflab.data.network.expressapi

import com.voxeldev.tinkofflab.domain.functional.Either
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderRequestModel
import com.voxeldev.tinkofflab.domain.repository.OrdersRepository

class OrdersRepositoryImpl : OrdersRepository {
    override fun getOrders(): Either<Exception, List<OrderModel>> {
        TODO("Not yet implemented")
    }

    override fun createOrder(order: OrderRequestModel): Either<Exception, OrderModel> {
        TODO("Not yet implemented")
    }

    override fun updateOrder(id: Int, order: OrderRequestModel): Either<Exception, OrderModel> {
        TODO("Not yet implemented")
    }
}
