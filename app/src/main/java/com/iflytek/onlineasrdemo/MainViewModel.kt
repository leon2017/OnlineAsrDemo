package com.iflytek.onlineasrdemo

import androidx.lifecycle.ViewModel
import com.iflytek.onlineasrdemo.asr.AsrHelper
import com.iflytek.onlineasrdemo.asr.AsrSpeechRecognizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState.default())

    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var state: MainUiState
        get() = _uiState.value
        set(value) {
            _uiState.update { value }
        }

    private var asrHelper = AsrHelper()

    init {
        asrHelper.setAsrSpeechRecognizer(asrSpeechRecognizer())
    }

    private fun asrSpeechRecognizer() = object : AsrSpeechRecognizer {

        override fun onStart() {
        }

        override fun onResult(text: String, last: Boolean) {
            state = state.copy(
                asrText = text
            )
            if (last) {
                //表示结束
                val asrText = state.asrText + "\n语音识别结束"
                state = state.copy(
                    asrText = asrText
                )
            }
        }

        override fun onFailure() {
            state = state.copy(
                asrText = "语音识别失败"
            )
        }
    }

    fun openAsr() {
        asrHelper.startSpeech()
    }

    fun stopAsr() {
        asrHelper.stopSpeech()
    }

    override fun onCleared() {
        super.onCleared()
        asrHelper.destroy()
    }

}