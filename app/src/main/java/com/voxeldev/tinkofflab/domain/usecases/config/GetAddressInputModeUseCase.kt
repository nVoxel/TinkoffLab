package com.voxeldev.tinkofflab.domain.usecases.config

import com.voxeldev.tinkofflab.domain.models.config.AddressInputMode
import com.voxeldev.tinkofflab.domain.repository.config.AddressInputModeRepository
import com.voxeldev.tinkofflab.domain.usecases.base.BaseUseCase
import com.voxeldev.tinkofflab.utils.functional.Either
import javax.inject.Inject

class GetAddressInputModeUseCase @Inject constructor(
    private val repository: AddressInputModeRepository
) : BaseUseCase<BaseUseCase.None, AddressInputMode>() {

    override fun run(params: None): Either<Exception, AddressInputMode> =
        repository.getValue()
}
