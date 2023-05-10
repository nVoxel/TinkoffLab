package com.voxeldev.tinkofflab.data.local

import android.content.Context
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.domain.models.catalog.CatalogItemModel
import com.voxeldev.tinkofflab.domain.repository.catalog.CatalogRepository
import com.voxeldev.tinkofflab.utils.functional.Either
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CatalogRepositoryMockImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CatalogRepository {

    private val resources = context.resources

    companion object {
        private const val KETTLE_PRICE_RUB = 3556
        private const val PHONE_PRICE_RUB = 19990
        private const val HAMMER_PRICE_RUB = 899
    }

    override fun getValue(): Either<Exception, List<CatalogItemModel>> = Either.Right(getItems())

    private fun getItems(): List<CatalogItemModel> = listOf(
        CatalogItemModel(
            resources.getString(R.string.cart_item_kettle),
            KETTLE_PRICE_RUB,
            imageRes = R.drawable.cart_item_kettle
        ),
        CatalogItemModel(
            resources.getString(R.string.cart_item_phone),
            PHONE_PRICE_RUB,
            imageRes = R.drawable.cart_item_phone
        ),
        CatalogItemModel(
            resources.getString(R.string.cart_item_hammer),
            HAMMER_PRICE_RUB,
            imageRes = R.drawable.cart_item_hammer
        )
    )
}
