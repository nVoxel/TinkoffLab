package com.voxeldev.tinkofflab.data.network.expressapi

import com.voxeldev.tinkofflab.data.mappers.expressapi.AddressMapper
import com.voxeldev.tinkofflab.data.mappers.expressapi.TimeSlotMapper
import com.voxeldev.tinkofflab.data.network.base.BaseRepository
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.ExpressService
import com.voxeldev.tinkofflab.utils.functional.Either
import com.voxeldev.tinkofflab.domain.models.expressapi.AddressModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.domain.repository.expressapi.DeliveryRepository
import com.voxeldev.tinkofflab.utils.platform.NetworkHandler
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(
    networkHandler: NetworkHandler,
    private val expressService: ExpressService,
    private val addressMapper: AddressMapper,
    private val timeSlotMapper: TimeSlotMapper
) : BaseRepository(networkHandler), DeliveryRepository {
    override fun getAddresses(): Either<Exception, List<AddressModel>> =
        request(
            expressService.getAddresses(),
        ) { addressResponse -> addressResponse.map { addressMapper.toModel(it) } }

    override fun searchAddress(address: AddressModel): Either<Exception, List<AddressModel>> =
        request(
            expressService.searchAddress(addressMapper.toRequest(address)),
        ) { addressResponse -> addressResponse.map { addressMapper.toModel(it) } }

    override fun getSlots(date: String): Either<Exception, List<TimeSlotModel>> =
        request(
            expressService.getSlots(date),
        ) { timeSlotResponse -> timeSlotResponse.map { timeSlotMapper.toModel(it) } }
}
