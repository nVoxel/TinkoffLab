package com.voxeldev.tinkofflab.domain.usecases.expressapi

import com.voxeldev.tinkofflab.utils.functional.Either
import com.voxeldev.tinkofflab.domain.models.expressapi.OrderModel
import com.voxeldev.tinkofflab.domain.repository.expressapi.OrdersRepository
import com.voxeldev.tinkofflab.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) : BaseUseCase<OrderModel, OrderModel>() {

    override fun run(params: OrderModel): Either<Exception, OrderModel> =
        ordersRepository.updateOrder(params)
}
