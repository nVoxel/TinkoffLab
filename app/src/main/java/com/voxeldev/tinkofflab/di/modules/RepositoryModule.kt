package com.voxeldev.tinkofflab.di.modules

import com.voxeldev.tinkofflab.data.network.expressapi.DeliveryRepositoryImpl
import com.voxeldev.tinkofflab.data.network.expressapi.OrdersRepositoryImpl
import com.voxeldev.tinkofflab.domain.repository.DeliveryRepository
import com.voxeldev.tinkofflab.domain.repository.OrdersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideDeliveryRepository(
        deliveryRepository: DeliveryRepositoryImpl
    ): DeliveryRepository

    @Binds
    abstract fun provideOrdersRepository(
        ordersRepository: OrdersRepositoryImpl
    ): OrdersRepository
}
