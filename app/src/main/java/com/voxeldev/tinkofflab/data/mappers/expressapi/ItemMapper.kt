package com.voxeldev.tinkofflab.data.mappers.expressapi

import com.voxeldev.tinkofflab.data.network.expressapi.datasource.models.ItemApiModel
import com.voxeldev.tinkofflab.domain.models.expressapi.ItemModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemMapper @Inject constructor() {

    @Throws(NullPointerException::class)
    fun toModel(item: ItemApiModel): ItemModel =
        ItemModel(
            item.name!!,
            item.price!!
        )

    fun toApiModel(item: ItemModel): ItemApiModel =
        ItemApiModel(
            item.name,
            item.price
        )
}
