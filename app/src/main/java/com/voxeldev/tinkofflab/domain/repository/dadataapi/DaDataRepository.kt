package com.voxeldev.tinkofflab.domain.repository.dadataapi

import com.voxeldev.tinkofflab.domain.models.AddressModel
import com.voxeldev.tinkofflab.utils.functional.Either

interface DaDataRepository {

    fun getSuggestions(query: Pair<String, String>): Either<Exception, List<AddressModel>>
}
