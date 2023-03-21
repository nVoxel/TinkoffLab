package com.voxeldev.tinkofflab.ui.delivery

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.github.terrakok.cicerone.Screen

data class DeliveryTypeModel(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    val nextScreen: Screen
)
