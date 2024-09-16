package com.example.weatherapp.cities.domain.repository

import com.example.weatherapp.cities.domain.model.City

interface CitiesRepository {
    suspend fun getCities(): Result<List<City>>
}