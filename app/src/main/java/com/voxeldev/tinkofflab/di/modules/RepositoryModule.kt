package com.voxeldev.tinkofflab.di.modules

import android.content.Context
import com.voxeldev.tinkofflab.data.local.AddressInputModeRepositoryImpl
import com.voxeldev.tinkofflab.data.local.CatalogRepositoryMockImpl
import com.voxeldev.tinkofflab.data.network.dadataapi.DaDataRepositoryImpl
import com.voxeldev.tinkofflab.data.network.expressapi.DeliveryRepositoryImpl
import com.voxeldev.tinkofflab.data.network.expressapi.OrdersRepositoryImpl
import com.voxeldev.tinkofflab.domain.repository.catalog.CatalogRepository
import com.voxeldev.tinkofflab.domain.repository.config.AddressInputModeRepository
import com.voxeldev.tinkofflab.domain.repository.dadataapi.DaDataRepository
import com.voxeldev.tinkofflab.domain.repository.expressapi.DeliveryRepository
import com.voxeldev.tinkofflab.domain.repository.expressapi.OrdersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [RepositoryModule.Provide::class])
@InstallIn(SingletonComponent::class)
@Suppress("UnnecessaryAbstractClass")
abstract class RepositoryModule {

    @Binds
    abstract fun bindDeliveryRepository(
        deliveryRepository: DeliveryRepositoryImpl
    ): DeliveryRepository

    @Binds
    abstract fun bindOrdersRepository(
        ordersRepository: OrdersRepositoryImpl
    ): OrdersRepository

    @Binds
    abstract fun bindDaDataRepository(
        daDataRepository: DaDataRepositoryImpl
    ): DaDataRepository

    @Binds
    abstract fun bindCatalogRepository(
        catalogRepository: CatalogRepositoryMockImpl
    ): CatalogRepository

    @Module
    @InstallIn(SingletonComponent::class)
    class Provide {

        @Provides
        @Singleton
        fun provideAddressInputModeRepository(
            @ApplicationContext context: Context
        ): AddressInputModeRepository = AddressInputModeRepositoryImpl(context)
    }
}

