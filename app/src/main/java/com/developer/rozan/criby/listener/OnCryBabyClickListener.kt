package com.developer.rozan.criby.listener

import android.view.View
import com.developer.rozan.criby.data.local.entity.CryBabyEntity

interface OnCryBabyClickListener {
    fun onItemClicked(cryBabyEntity: CryBabyEntity)
}