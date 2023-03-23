package com.voxeldev.tinkofflab.data.network.dadataapi.mapper

import com.voxeldev.tinkofflab.data.network.dadataapi.datasource.responses.SuggestionsResponse
import com.voxeldev.tinkofflab.domain.models.AddressModel

fun SuggestionsResponse.toAddressModel() = suggestions.map {
    val streetWithHouse = it.data.streetWithType?.let { streetWithType ->
        buildString {
            append(streetWithType)
            it.data.houseType?.let { houseType ->
                append(", $houseType ${it.data.house}")
            }
        }
    } ?: "-"
    AddressModel(
        street = streetWithHouse,
        fullAddress = it.value ?: ""
    )
}
