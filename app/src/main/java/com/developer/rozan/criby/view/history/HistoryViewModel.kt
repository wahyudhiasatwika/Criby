package com.developer.rozan.criby.view.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.developer.rozan.criby.data.local.entity.HistoryAudioEntity
import com.developer.rozan.criby.data.local.room.HistoryAudioRepository

class HistoryViewModel(application: Application) : ViewModel() {

    private val mHistoryAudioRepository: HistoryAudioRepository =
        HistoryAudioRepository(application)

    fun getAllResultHistory(): LiveData<List<HistoryAudioEntity>> =
        mHistoryAudioRepository.getAllResultHistory()

    fun deleteResultHistory(historyAudioEntity: HistoryAudioEntity) {
        mHistoryAudioRepository.deleteResultHistory(historyAudioEntity)
    }
}