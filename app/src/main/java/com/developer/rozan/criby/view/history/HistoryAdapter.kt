package com.developer.rozan.criby.view.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.rozan.criby.data.local.entity.HistoryAudioEntity
import com.developer.rozan.criby.databinding.ItemHistoryBinding
import com.developer.rozan.criby.listener.OnHistoryClickListener

class HistoryAdapter(private val historyAudioList: List<HistoryAudioEntity>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    var listener: OnHistoryClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return historyAudioList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historyAudio = historyAudioList[position]
        holder.bind(historyAudio)

        holder.clHistory.setOnClickListener {
            listener?.onItemClicked(historyAudio)
        }
    }

    class ViewHolder(private val view: ItemHistoryBinding) : RecyclerView.ViewHolder(view.root) {

        val clHistory = view.clHistory

        fun bind(historyAudioEntity: HistoryAudioEntity) {
            view.resultText.text = historyAudioEntity.klasifikasiPredict
        }
    }
}