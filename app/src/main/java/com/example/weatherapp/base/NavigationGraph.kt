package com.example.weatherapp.base

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.cities.presentation.CitiesScreenImp
import kotlinx.serialization.Serializable


@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = CitiesScreen
    ) {
        composable<CitiesScreen> {
            CitiesScreenImp()
        }
    }

}

@Serializable
object CitiesScreen