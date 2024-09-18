package com.iflytek.onlineasrdemo

data class MainUiState(
    val asrText: String
) {
    companion object {

        fun default(): MainUiState {
            return MainUiState(
                asrText = ""
            )
        }
    }
}