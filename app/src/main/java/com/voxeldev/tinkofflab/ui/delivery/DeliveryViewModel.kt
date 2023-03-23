package com.voxeldev.tinkofflab.ui.delivery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.voxeldev.tinkofflab.domain.usecases.GetAddressSuggestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val getAddressSuggestionsUseCase: GetAddressSuggestionsUseCase
) : ViewModel() {

    private val _suggestionsFlow = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val suggestions = _suggestionsFlow
        .filterNot { it.isNullOrBlank() }
        .distinctUntilChanged()
        .debounce(SEARCH_TIMEOUT)
        .flatMapLatest { getAddressSuggestionsUseCase(it!!) }
        .asLiveData(viewModelScope.coroutineContext)

    fun getSuggestions(query: String?) {
        _suggestionsFlow.value = query
    }

    companion object {
        private const val SEARCH_TIMEOUT = 1000L // millis
    }
}
