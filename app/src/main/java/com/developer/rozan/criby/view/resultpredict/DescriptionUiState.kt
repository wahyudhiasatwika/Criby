package com.developer.rozan.criby.view.resultpredict

sealed interface DescriptionUiState {
    data object Loading : DescriptionUiState
    data class Success(
        val outputText: String
    ) : DescriptionUiState

    data class Error(
        val errorMessage: String
    ) : DescriptionUiState
}