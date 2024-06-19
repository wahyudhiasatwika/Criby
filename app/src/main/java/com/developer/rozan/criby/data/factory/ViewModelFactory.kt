package com.developer.rozan.criby.data.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developer.rozan.criby.view.chatbot.ChatbotViewModel
import com.developer.rozan.criby.view.history.HistoryViewModel
import com.developer.rozan.criby.view.recordaudio.RecordAudioViewModel
import com.developer.rozan.criby.view.resultpredict.ResultPredictViewModel
import com.google.ai.client.generativeai.GenerativeModel

class ViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash-latest",
            apiKey = "AIzaSyAn5ncK_AEm7sAvSgsRJm42Kr1-72UTKdw"
        )
        if (modelClass.isAssignableFrom(ChatbotViewModel::class.java)) {
            return ChatbotViewModel(generativeModel) as T
        } else if (modelClass.isAssignableFrom(RecordAudioViewModel::class.java)) {
            return RecordAudioViewModel(mApplication, generativeModel) as T
        } else if (modelClass.isAssignableFrom(ResultPredictViewModel::class.java)) {
            return ResultPredictViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(mApplication) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}