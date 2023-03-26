package com.voxeldev.tinkofflab.data.mappers.expressapi

import com.voxeldev.tinkofflab.data.network.expressapi.datasource.responses.ItemResponse
import com.voxeldev.tinkofflab.domain.models.expressapi.ItemModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemMapper @Inject constructor() {

    @Throws(NullPointerException::class)
    fun toModel(item: ItemResponse): ItemModel =
        ItemModel(
            item.name!!,
            item.price!!
        )

    fun toResponse(item: ItemModel): ItemResponse =
        ItemResponse(
            item.name,
            item.price
        )
}
