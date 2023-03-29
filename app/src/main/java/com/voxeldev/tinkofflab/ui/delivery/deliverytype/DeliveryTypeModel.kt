package com.voxeldev.tinkofflab.ui.delivery.deliverytype

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.voxeldev.tinkofflab.ui.utils.OnClick

data class DeliveryTypeModel(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    val onClick: OnClick
)
