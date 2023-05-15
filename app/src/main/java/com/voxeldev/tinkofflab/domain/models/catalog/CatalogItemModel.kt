package com.voxeldev.tinkofflab.domain.models.catalog

import androidx.annotation.DrawableRes

data class CatalogItemModel(
    val name: String,
    val price: Int,
    val imageUrl: String? = null,
    @DrawableRes val imageRes: Int? = null
)
