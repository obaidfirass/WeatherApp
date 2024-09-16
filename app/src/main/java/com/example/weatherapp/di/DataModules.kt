package com.example.weatherapp.di

import com.example.weatherapp.cities.data.repository.DefaultCitiesRepository
import com.example.weatherapp.cities.domain.repository.CitiesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindTaskRepository(repository: DefaultCitiesRepository): CitiesRepository
}

