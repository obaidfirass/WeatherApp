package com.example.weatherapp.cities.data.repository

import android.content.Context
import com.example.weatherapp.cities.domain.model.CitiesResponse
import com.example.weatherapp.cities.domain.model.City
import com.example.weatherapp.cities.domain.repository.CitiesRepository
import com.example.weatherapp.di.DefaultDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCitiesRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : CitiesRepository {
    override suspend fun getCities(): Result<List<City>> = withContext(dispatcher) {
        try {
            delay(2000)
            val jsonString = readJsonFile("cities.txt")
            val response = Json.decodeFromString<CitiesResponse>(jsonString)
            Result.success(response.cities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun readJsonFile(filename: String): String {
        return context.assets.open(filename).bufferedReader().use { it.readText() }
    }
}
