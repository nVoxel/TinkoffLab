package com.voxeldev.tinkofflab.data.network.dadataapi.datasource.responses


import com.google.gson.annotations.SerializedName

data class SuggestionsResponse(
    @SerializedName("suggestions")
    val suggestions: List<SuggestionResponse>
)
