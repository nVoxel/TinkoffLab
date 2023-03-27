package com.voxeldev.tinkofflab.domain.usecases.expressapi

import com.voxeldev.tinkofflab.utils.functional.Either
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.domain.repository.expressapi.DeliveryRepository
import com.voxeldev.tinkofflab.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetSlotsUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) : BaseUseCase<String, List<TimeSlotModel>>() {

    override suspend fun run(params: String): Either<Exception, List<TimeSlotModel>> =
        deliveryRepository.getSlots(params)
}
