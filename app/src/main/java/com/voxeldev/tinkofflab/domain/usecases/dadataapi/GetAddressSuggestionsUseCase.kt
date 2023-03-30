package com.voxeldev.tinkofflab.domain.usecases.dadataapi

import com.voxeldev.tinkofflab.domain.models.dadataapi.AddressModel
import com.voxeldev.tinkofflab.domain.repository.dadataapi.DaDataRepository
import com.voxeldev.tinkofflab.domain.usecases.base.BaseUseCase
import com.voxeldev.tinkofflab.ui.utils.Query
import com.voxeldev.tinkofflab.utils.functional.Either
import javax.inject.Inject

class GetAddressSuggestionsUseCase @Inject constructor(
    private val repository: DaDataRepository,
) : BaseUseCase<Query, List<AddressModel>>() {

    override fun run(params: Query): Either<Exception, List<AddressModel>> =
        repository.getSuggestions(params)
}
