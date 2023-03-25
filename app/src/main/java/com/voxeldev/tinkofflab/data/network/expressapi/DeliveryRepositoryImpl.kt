package com.voxeldev.tinkofflab.data.network.expressapi

import com.voxeldev.tinkofflab.domain.functional.Either
import com.voxeldev.tinkofflab.domain.models.expressapi.AddressModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.domain.repository.DeliveryRepository

class DeliveryRepositoryImpl : DeliveryRepository {
    override fun getAddresses(): Either<Exception, List<AddressModel>> {
        TODO("Not yet implemented")
    }

    override fun searchAddress(address: AddressModel): Either<Exception, List<AddressModel>> {
        TODO("Not yet implemented")
    }

    override fun getSlots(date: String): Either<Exception, List<TimeSlotModel>> {
        TODO("Not yet implemented")
    }
}
