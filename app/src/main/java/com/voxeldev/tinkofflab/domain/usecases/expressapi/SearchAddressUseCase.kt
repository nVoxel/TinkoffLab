package com.voxeldev.tinkofflab.domain.usecases.expressapi

import com.voxeldev.tinkofflab.utils.functional.Either
import com.voxeldev.tinkofflab.domain.models.expressapi.AddressModel
import com.voxeldev.tinkofflab.domain.repository.expressapi.DeliveryRepository
import com.voxeldev.tinkofflab.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class SearchAddressUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) : BaseUseCase<AddressModel, List<AddressModel>>() {

    override suspend fun run(params: AddressModel): Either<Exception, List<AddressModel>> =
        deliveryRepository.searchAddress(params)
}
