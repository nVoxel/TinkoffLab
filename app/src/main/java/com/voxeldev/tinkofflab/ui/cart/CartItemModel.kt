package com.voxeldev.tinkofflab.ui.cart

import com.voxeldev.tinkofflab.domain.models.catalog.CatalogItemModel

data class CartItemModel(
    val itemModel: CatalogItemModel,
    var count: Int = 0
)
