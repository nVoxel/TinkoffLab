package com.voxeldev.tinkofflab.ui.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.voxeldev.tinkofflab.domain.models.catalog.CatalogItemModel
import com.voxeldev.tinkofflab.domain.usecases.base.BaseUseCase
import com.voxeldev.tinkofflab.domain.usecases.catalog.GetCatalogUseCase
import com.voxeldev.tinkofflab.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCatalogUseCase: GetCatalogUseCase
) : BaseViewModel() {

    private val _items = MutableLiveData<List<CatalogItemModel>>()
    val items: LiveData<List<CatalogItemModel>>
        get() = _items

    fun getCatalogItems() {
        getCatalogUseCase(BaseUseCase.None, viewModelScope) { either ->
            either.fold(::handleException) {
                _items.value = it
            }
        }
    }
}
