package com.voxeldev.tinkofflab.utils.providers.string

import android.content.Context
import com.voxeldev.tinkofflab.R

class StringResourceProviderContextImpl(context: Context) : StringResourceProvider {
    private val resources = context.resources

    override fun getCartItemKettleString(): String = resources.getString(R.string.cart_item_kettle)

    override fun getCartItemPhoneString(): String = resources.getString(R.string.cart_item_phone)

    override fun getCartItemHammerString(): String = resources.getString(R.string.cart_item_hammer)
}
