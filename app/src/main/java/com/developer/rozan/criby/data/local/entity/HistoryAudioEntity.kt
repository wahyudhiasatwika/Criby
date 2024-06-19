package com.developer.rozan.criby.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tb_history_audio")
data class HistoryAudioEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "audio_uri")
    val audioUri: String,

    @ColumnInfo(name = "klasifikasi_predict")
    val klasifikasiPredict: String,

    @ColumnInfo(name = "confidence_predict")
    val confidencePredict: Double,

    @ColumnInfo(name = "desc_predict")
    val descPredict: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable
