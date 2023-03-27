package com.voxeldev.tinkofflab.data.mappers.expressapi

import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.AddressSearchRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.models.AddressApiModel
import com.voxeldev.tinkofflab.domain.models.expressapi.AddressModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressMapper @Inject constructor() {

    @Throws(NullPointerException::class)
    fun toModel(address: AddressApiModel): AddressModel =
        AddressModel(
            address.address!!,
            address.lat!!,
            address.lon!!
        )

    fun toApiModel(address: AddressModel): AddressApiModel =
        AddressApiModel(
            address.address,
            address.latitude,
            address.longitude
        )

    fun toRequest(address: AddressModel): AddressSearchRequest =
        AddressSearchRequest(
            address.address,
            address.latitude,
            address.longitude
        )
}
