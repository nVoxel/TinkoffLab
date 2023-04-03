package com.voxeldev.tinkofflab.data.mappers.dadataapi

import com.voxeldev.tinkofflab.data.network.dadataapi.datasource.responses.SuggestionsResponse
import com.voxeldev.tinkofflab.domain.models.dadataapi.AddressModel

const val DEFAULT_LATITUDE = 0.0f
const val DEFAULT_LONGITUDE = 0.0f

fun SuggestionsResponse.toAddressModel() = suggestions.map {
    val streetWithHouse =
        (it.data.streetWithType ?: it.data.settlementWithType)
            ?.let { streetWithType ->
                buildString {
                    append(streetWithType)
                    it.data.houseType?.let { houseType ->
                        append(", $houseType ${it.data.house}")
                    }
                }
            } ?: "-"
    AddressModel(
        street = streetWithHouse,
        fullAddress = it.value ?: "",
        latitude = it.data.geoLat?.toFloat() ?: DEFAULT_LATITUDE,
        longitude = it.data.geoLon?.toFloat() ?: DEFAULT_LONGITUDE
    )
}
