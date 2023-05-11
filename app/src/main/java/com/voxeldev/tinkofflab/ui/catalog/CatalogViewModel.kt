package com.voxeldev.tinkofflab.ui.catalog

import androidx.lifecycle.viewModelScope
import com.voxeldev.tinkofflab.domain.models.catalog.CatalogItemModel
import com.voxeldev.tinkofflab.domain.usecases.base.BaseUseCase
import com.voxeldev.tinkofflab.domain.usecases.catalog.GetCatalogUseCase
import com.voxeldev.tinkofflab.ui.base.BaseViewModel
import com.voxeldev.tinkofflab.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCatalogUseCase: GetCatalogUseCase
) : BaseViewModel() {

    val items = SingleLiveEvent<List<CatalogItemModel>>()

    fun getCatalogItems() {
        getCatalogUseCase(BaseUseCase.None, viewModelScope) { either ->
            either.fold(::handleException) {
                items.value = it
            }
        }
    }
}
