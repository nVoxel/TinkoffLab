package com.voxeldev.tinkofflab.data.network.dadataapi

import com.voxeldev.tinkofflab.data.mappers.dadataapi.toAddressModel
import com.voxeldev.tinkofflab.data.network.base.BaseRepository
import com.voxeldev.tinkofflab.data.network.dadataapi.datasource.DaDataApi
import com.voxeldev.tinkofflab.data.network.dadataapi.datasource.requests.AddressSuggestionRequest
import com.voxeldev.tinkofflab.domain.models.dadataapi.AddressModel
import com.voxeldev.tinkofflab.domain.repository.dadataapi.DaDataRepository
import com.voxeldev.tinkofflab.ui.utils.Query
import com.voxeldev.tinkofflab.utils.functional.Either
import com.voxeldev.tinkofflab.utils.platform.NetworkHandler
import javax.inject.Inject

class DaDataRepositoryImpl @Inject constructor(
    networkHandler: NetworkHandler,
    private val api: DaDataApi
) : BaseRepository(networkHandler), DaDataRepository {

    override fun getSuggestions(query: Query): Either<Exception, List<AddressModel>> =
        request(
            api.getSuggestions(AddressSuggestionRequest(query.first, query.second))
        ) { it.toAddressModel() }
}
