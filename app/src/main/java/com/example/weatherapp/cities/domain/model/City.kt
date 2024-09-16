package com.example.weatherapp.cities.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class City(
    val city: String,
    val id: String
) : Parcelable

@Serializable
data class CitiesResponse(
    val cities: List<City>
)