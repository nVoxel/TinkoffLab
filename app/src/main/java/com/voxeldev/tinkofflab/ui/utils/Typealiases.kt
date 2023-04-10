package com.voxeldev.tinkofflab.ui.utils

typealias OnClick = () -> Unit

typealias OnAddressClick = (address: DaDataAddressModel) -> Unit

typealias DaDataAddressModel = com.voxeldev.tinkofflab.domain.models.dadataapi.AddressModel

typealias ExpressAddressModel = com.voxeldev.tinkofflab.domain.models.expressapi.AddressModel

// query to language
typealias Query = Pair<String, String>
