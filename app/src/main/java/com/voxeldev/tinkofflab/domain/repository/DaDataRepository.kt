package com.voxeldev.tinkofflab.domain.repository

import com.voxeldev.tinkofflab.domain.models.AddressModel

interface DaDataRepository {
    suspend fun getSuggestions(query: String): List<AddressModel>
}
