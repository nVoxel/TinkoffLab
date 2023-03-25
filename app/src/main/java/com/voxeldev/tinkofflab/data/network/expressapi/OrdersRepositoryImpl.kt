package com.voxeldev.tinkofflab.data.network.expressapi

import com.voxeldev.tinkofflab.data.mappers.OrderMapper
import com.voxeldev.tinkofflab.data.network.expressapi.base.ExpressRepositoryBase
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.ExpressService
import com.voxeldev.tinkofflab.domain.functional.Either
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.repository.OrdersRepository
import com.voxeldev.tinkofflab.utils.platform.NetworkHandler
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    networkHandler: NetworkHandler,
    private val expressService: ExpressService,
    private val orderMapper: OrderMapper
) : ExpressRepositoryBase(networkHandler), OrdersRepository {
    override fun getOrders(): Either<Exception, List<OrderModel>> =
        request(
            expressService.getOrders(),
        ) { orderResponse -> orderResponse.map { orderMapper.toModel(it) } }

    override fun createOrder(order: OrderModel): Either<Exception, OrderModel> =
        request(
            expressService.createOrder(orderMapper.toCreateRequest(order)),
        ) { orderMapper.toModel(it) }

    override fun updateOrder(id: Int, order: OrderModel): Either<Exception, OrderModel> =
        request(
            expressService.updateOrder(id, orderMapper.toUpdateRequest(order)),
        ) { orderMapper.toModel(it) }
}
