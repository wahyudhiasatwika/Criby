package com.developer.rozan.criby.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PredictionResponse(

    @field:SerializedName("confidence")
    val confidence: Double,

    @field:SerializedName("predictions")
    val predictions: String,

    @field:SerializedName("error")
    val error: String? = null
) : Parcelable
