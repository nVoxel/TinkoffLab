package com.voxeldev.tinkofflab.data.mappers

import com.voxeldev.tinkofflab.data.network.expressapi.datasource.requests.AddressSearchRequest
import com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses.AddressResponse
import com.voxeldev.tinkofflab.domain.models.expressapi.AddressModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressMapper @Inject constructor() {

    @Throws(NullPointerException::class)
    fun toModel(address: AddressResponse): AddressModel =
        AddressModel(
            address.address!!,
            address.lat!!,
            address.lon!!
        )

    fun toResponse(address: AddressModel): AddressResponse =
        AddressResponse(
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
