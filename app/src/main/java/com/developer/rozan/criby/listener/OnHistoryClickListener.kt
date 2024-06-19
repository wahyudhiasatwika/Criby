package com.developer.rozan.criby.listener

import com.developer.rozan.criby.data.local.entity.HistoryAudioEntity

interface OnHistoryClickListener {

    fun onItemClicked(historyAudioEntity: HistoryAudioEntity)
}