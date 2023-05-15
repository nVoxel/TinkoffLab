package com.voxeldev.tinkofflab.domain.usecases.catalog

import com.voxeldev.tinkofflab.domain.models.catalog.CatalogItemModel
import com.voxeldev.tinkofflab.domain.repository.catalog.CatalogRepository
import com.voxeldev.tinkofflab.domain.usecases.base.BaseUseCase
import com.voxeldev.tinkofflab.utils.functional.Either
import javax.inject.Inject

class GetCatalogUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) : BaseUseCase<BaseUseCase.None, List<CatalogItemModel>>() {

    override fun run(params: None): Either<Exception, List<CatalogItemModel>> =
        catalogRepository.getValue()
}
