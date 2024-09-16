package com.example.weatherapp.cities.presentation

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.cities.domain.model.City
import com.example.weatherapp.cities.domain.repository.CitiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val repo: CitiesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        savedStateHandle.get<CitiesUiState>(CITIES_SAVED_STATE_KEY) ?: CitiesUiState()
    )
    val uiState: StateFlow<CitiesUiState> = _uiState.asStateFlow()

    fun getCities() {
        if (_uiState.value.items.isEmpty()) {
            _uiState.update { _uiState.value.copy(isLoading = true) }
            viewModelScope.launch {
                repo.getCities()
                    .onSuccess { cities ->
                        _uiState.update { state ->
                            state.copy(items = cities, isLoading = false)
                        }
                    }
                    .onFailure {
                        _uiState.update { state ->
                            state.copy(isLoading = false, isError = true)
                        }
                    }.apply {
                        savedStateHandle[CITIES_SAVED_STATE_KEY] = _uiState.value
                    }
            }
        }
    }
}

const val CITIES_SAVED_STATE_KEY = "cities_state_key"

@Parcelize
data class CitiesUiState(
    val items: List<City> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean? = false
) : Parcelable