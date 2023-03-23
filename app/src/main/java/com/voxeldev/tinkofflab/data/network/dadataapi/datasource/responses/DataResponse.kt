package com.voxeldev.tinkofflab.data.network.dadataapi.datasource.responses


import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("country_iso_code")
    val countryIsoCode: String?,
    @SerializedName("flat")
    val flat: Any?,
    @SerializedName("geo_lat")
    val geoLat: String?,
    @SerializedName("geo_lon")
    val geoLon: String?,
    @SerializedName("postal_code")
    val postalCode: String?,
    @SerializedName("region")
    val region: String?,
    @SerializedName("room")
    val room: Any?,
    @SerializedName("street_with_type")
    val streetWithType: String?,
    @SerializedName("house_type")
    val houseType: String?,
    @SerializedName("house")
    val house: String?,
)
