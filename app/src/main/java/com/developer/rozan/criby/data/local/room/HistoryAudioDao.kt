package com.developer.rozan.criby.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.developer.rozan.criby.data.local.entity.HistoryAudioEntity

@Dao
interface HistoryAudioDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertHistoryAudio(historyAudioEntity: HistoryAudioEntity)

    @Query("SELECT * FROM tb_history_audio ORDER BY timestamp DESC")
    fun getAllHistoryAudio(): LiveData<List<HistoryAudioEntity>>

    @Delete
    fun deleteHistoryAudio(historyAudioEntity: HistoryAudioEntity)
}