package com.developer.rozan.criby.view.recordaudio

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.rozan.criby.data.remote.response.BaseResponse
import com.developer.rozan.criby.data.remote.response.PredictionResponse
import com.developer.rozan.criby.data.remote.retrofit.ApiConfig
import com.developer.rozan.criby.view.resultpredict.DescriptionUiState
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class RecordAudioViewModel(
    private val application: Application,
    private val generativeModel: GenerativeModel
) : ViewModel() {

    private val _resultUploadSound = MutableLiveData<BaseResponse>()
    val resultUploadSound: LiveData<BaseResponse> = _resultUploadSound

    private val _uiState = MutableLiveData<DescriptionUiState>()
    val uiState: LiveData<DescriptionUiState> get() = _uiState

    fun uploadSound(soundMultipart: MultipartBody.Part) {
        viewModelScope.launch {
            if (!isInternetAvailable()) {
                _resultUploadSound.value =
                    BaseResponse.Error(Throwable("Tidak ada jaringan internet, silahkan rekam suara lagi."))
                return@launch
            }

            flow {
                val response =
                    ApiConfig.getApiService().predict(soundMultipart)
                emit(response)
            }.onStart {
                _resultUploadSound.value = BaseResponse.Loading(true)
            }.onCompletion {
                _resultUploadSound.value = BaseResponse.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                _resultUploadSound.value = BaseResponse.Error(it)
            }.collect {
                _resultUploadSound.value = BaseResponse.Success(it)
            }
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun getDescPrediksi(predictionResponse: PredictionResponse) {
        val prompt = "Klasifikasi: ${predictionResponse.predictions}" +
                "Persentase: ${predictionResponse.confidence}" +
                "Target: Orang tua yang memiliki bayi" +
                "Berikan sebuah kalimat yang baik berdasarkan teks di atas agar orang tuanya dapat memahami si bayi!"

        viewModelScope.launch {
            try {
                _uiState.value = DescriptionUiState.Loading
                var outputContent = ""
                generativeModel.generateContentStream(prompt)
                    .collect { response ->
                        outputContent += response.text
                        _uiState.value = DescriptionUiState.Success(outputContent)
                    }
            } catch (e: Exception) {
                _uiState.value = DescriptionUiState.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }
}