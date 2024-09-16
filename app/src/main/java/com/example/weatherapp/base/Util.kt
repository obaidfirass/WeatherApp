package com.example.weatherapp.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoadingIndicator(
    isLoading: Boolean,
    isError: Boolean,
    onRefresh: () -> Unit,
    errorContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    if (isError) {
        errorContent()
    } else {
        val pullRefreshState = rememberPullRefreshState(
            refreshing = isLoading,
            onRefresh = onRefresh
        )

        Box(
            modifier = Modifier.pullRefresh(pullRefreshState)
        ) {
            content()
            PullRefreshIndicator(
                refreshing = isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@Composable
fun ErrorContent() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Error, please try again",
            color = Color.Red,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun ErrorContentPreview() {
    ErrorContent()
}

@Preview
@Composable
fun LoadingIndicatorPreview() {
    LoadingIndicator(
        isLoading = true,
        isError = false,
        onRefresh = {},
        errorContent = { ErrorContent() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Content",
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}