package com.voxeldev.tinkofflab.ui.delivery

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.voxeldev.tinkofflab.ui.utils.onClick

data class DeliveryTypeModel(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    val onClick: onClick
)
