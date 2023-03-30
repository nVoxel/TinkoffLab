package com.voxeldev.tinkofflab.data.mappers.dadataapi

import com.voxeldev.tinkofflab.data.network.dadataapi.datasource.responses.SuggestionsResponse
import com.voxeldev.tinkofflab.domain.models.dadataapi.AddressModel

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
        fullAddress = it.value ?: ""
    )
}
