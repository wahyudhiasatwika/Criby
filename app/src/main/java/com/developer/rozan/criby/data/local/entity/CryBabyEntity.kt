package com.developer.rozan.criby.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryBabyEntity(
    val title: String,
    val description: String,
    val reference: String,
    val image: Int
) : Parcelable
