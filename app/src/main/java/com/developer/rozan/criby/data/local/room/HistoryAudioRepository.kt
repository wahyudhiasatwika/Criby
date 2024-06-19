package com.developer.rozan.criby.data.local.room

import android.app.Application
import androidx.lifecycle.LiveData
import com.developer.rozan.criby.data.local.entity.HistoryAudioEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryAudioRepository(application: Application) {
    private val mHistoryAudioDao: HistoryAudioDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = HistoryAudioRoomDatabase.getDatabase(application)
        mHistoryAudioDao = db.historyAudioDao()
    }

    fun saveResultHistory(historyAudioEntity: HistoryAudioEntity) {
        executorService.execute { mHistoryAudioDao.insertHistoryAudio(historyAudioEntity) }
    }

    fun getAllResultHistory(): LiveData<List<HistoryAudioEntity>> =
        mHistoryAudioDao.getAllHistoryAudio()

    fun deleteResultHistory(historyAudioEntity: HistoryAudioEntity) {
        executorService.execute { mHistoryAudioDao.deleteHistoryAudio(historyAudioEntity) }
    }
}