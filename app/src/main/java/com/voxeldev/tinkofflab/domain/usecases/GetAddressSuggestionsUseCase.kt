package com.voxeldev.tinkofflab.domain.usecases

import com.voxeldev.tinkofflab.domain.repository.DaDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAddressSuggestionsUseCase(
    private val repository: DaDataRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(query: String) = flow {
        emit(repository.getSuggestions(query))
    }.flowOn(dispatcher)
}
