package com.voxeldev.tinkofflab.data.local

import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.domain.models.catalog.CatalogItemModel
import com.voxeldev.tinkofflab.domain.repository.catalog.CatalogRepository
import com.voxeldev.tinkofflab.utils.functional.Either
import com.voxeldev.tinkofflab.utils.providers.string.StringResourceProvider
import javax.inject.Inject

class CatalogRepositoryMockImpl @Inject constructor(
    private val stringResourceProvider: StringResourceProvider
) : CatalogRepository {

    companion object {
        private const val KETTLE_PRICE_RUB = 3556
        private const val PHONE_PRICE_RUB = 19990
        private const val HAMMER_PRICE_RUB = 899
    }

    override fun getValue(): Either<Exception, List<CatalogItemModel>> = Either.Right(getItems())

    private fun getItems(): List<CatalogItemModel> = listOf(
        CatalogItemModel(
            stringResourceProvider.getCartItemKettleString(),
            KETTLE_PRICE_RUB,
            imageRes = R.drawable.cart_item_kettle
        ),
        CatalogItemModel(
            stringResourceProvider.getCartItemPhoneString(),
            PHONE_PRICE_RUB,
            imageRes = R.drawable.cart_item_phone
        ),
        CatalogItemModel(
            stringResourceProvider.getCartItemHammerString(),
            HAMMER_PRICE_RUB,
            imageRes = R.drawable.cart_item_hammer
        )
    )
}
