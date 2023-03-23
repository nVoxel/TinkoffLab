package com.voxeldev.tinkofflab.data.network.dadataapi

import com.voxeldev.tinkofflab.data.network.dadataapi.datasource.DaDataApi
import com.voxeldev.tinkofflab.data.network.dadataapi.datasource.requests.QueryRequest
import com.voxeldev.tinkofflab.data.network.dadataapi.mapper.toAddressModel
import com.voxeldev.tinkofflab.domain.models.AddressModel
import com.voxeldev.tinkofflab.domain.repository.DaDataRepository
import javax.inject.Inject

class DaDataRepositoryImpl @Inject constructor(
    private val api: DaDataApi
) : DaDataRepository {
    override suspend fun getSuggestions(query: String): List<AddressModel> =
        api.getSuggestions(QueryRequest(query)).toAddressModel()
}
