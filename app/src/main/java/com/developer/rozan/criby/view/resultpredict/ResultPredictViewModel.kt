package com.developer.rozan.criby.view.resultpredict

import android.app.Application
import androidx.lifecycle.ViewModel
import com.developer.rozan.criby.data.local.entity.HistoryAudioEntity
import com.developer.rozan.criby.data.local.room.HistoryAudioRepository

class ResultPredictViewModel(
    application: Application
) :
    ViewModel() {

    private val mHistoryAudioRepository: HistoryAudioRepository =
        HistoryAudioRepository(application)

    fun insertResult(historyAudioEntity: HistoryAudioEntity) {
        mHistoryAudioRepository.saveResultHistory(historyAudioEntity)
    }
}