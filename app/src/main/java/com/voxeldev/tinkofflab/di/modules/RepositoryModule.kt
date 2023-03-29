package com.voxeldev.tinkofflab.di.modules

import com.voxeldev.tinkofflab.data.network.dadataapi.DaDataRepositoryImpl
import com.voxeldev.tinkofflab.data.network.expressapi.DeliveryRepositoryImpl
import com.voxeldev.tinkofflab.data.network.expressapi.OrdersRepositoryImpl
import com.voxeldev.tinkofflab.domain.repository.dadataapi.DaDataRepository
import com.voxeldev.tinkofflab.domain.repository.expressapi.DeliveryRepository
import com.voxeldev.tinkofflab.domain.repository.expressapi.OrdersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@Suppress("UnnecessaryAbstractClass")
abstract class RepositoryModule {

    @Binds
    abstract fun provideDeliveryRepository(
        deliveryRepository: DeliveryRepositoryImpl
    ): DeliveryRepository

    @Binds
    abstract fun provideOrdersRepository(
        ordersRepository: OrdersRepositoryImpl
    ): OrdersRepository

    @Binds
    abstract fun provideDaDataRepository(
        daDataRepository: DaDataRepositoryImpl
    ): DaDataRepository
}
