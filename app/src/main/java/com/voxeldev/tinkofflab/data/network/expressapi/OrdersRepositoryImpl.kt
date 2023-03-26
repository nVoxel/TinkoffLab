package com.voxeldev.tinkofflab.data.network.expressapi

import com.voxeldev.tinkofflab.data.mappers.expressapi.OrderMapper
import com.voxeldev.tinkofflab.data.network.base.BaseRepository
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.ExpressService
import com.voxeldev.tinkofflab.utils.functional.Either
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.repository.expressapi.OrdersRepository
import com.voxeldev.tinkofflab.utils.platform.NetworkHandler
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    networkHandler: NetworkHandler,
    private val expressService: ExpressService,
    private val orderMapper: OrderMapper
) : BaseRepository(networkHandler), OrdersRepository {
    override fun getOrders(): Either<Exception, List<OrderModel>> =
        request(
            expressService.getOrders(),
        ) { orderResponse -> orderResponse.map { orderMapper.toModel(it) } }

    override fun createOrder(order: OrderModel): Either<Exception, OrderModel> =
        request(
            expressService.createOrder(orderMapper.toCreateRequest(order)),
        ) { orderMapper.toModel(it) }

    override fun updateOrder(order: OrderModel): Either<Exception, OrderModel> =
        request(
            expressService.updateOrder(order.id, orderMapper.toUpdateRequest(order)),
        ) { orderMapper.toModel(it) }
}
