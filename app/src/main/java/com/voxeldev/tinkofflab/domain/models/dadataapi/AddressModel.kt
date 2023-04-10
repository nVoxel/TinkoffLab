package com.voxeldev.tinkofflab.domain.models.dadataapi

data class AddressModel(
    val street: String = "-",
    val fullAddress: String,
    val latitude: Float,
    val longitude: Float
)
