package com.developer.rozan.criby.view.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.rozan.criby.data.factory.ViewModelFactory
import com.developer.rozan.criby.data.local.entity.HistoryAudioEntity
import com.developer.rozan.criby.databinding.ActivityHistoryBinding
import com.developer.rozan.criby.listener.OnHistoryClickListener
import com.developer.rozan.criby.utils.HISTORY_AUDIO_ENTITY
import com.developer.rozan.criby.utils.PAR2
import com.developer.rozan.criby.utils.PARAM
import com.developer.rozan.criby.utils.gone
import com.developer.rozan.criby.utils.visible
import com.developer.rozan.criby.view.resultpredict.ResultPredictActivity
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity(), OnHistoryClickListener {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        historyViewModel = obtainViewModel(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setUpHistory()
            }
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setUpHistory() {
        binding.apply {
            rvHistory.visible()
            emptyLayout.clEmpty.gone()

            rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
            rvHistory.setHasFixedSize(true)
        }

        historyViewModel.getAllResultHistory().observe(this) { resultHistoryList ->
            if (resultHistoryList.isNotEmpty()) {
                historyAdapter = HistoryAdapter(resultHistoryList)
                historyAdapter.listener = this
                binding.rvHistory.adapter = historyAdapter
            } else {
                showEmptyLayout("Tidak ada Riwayat Analisis.")
            }
        }
    }

    private fun showEmptyLayout(msg: String) {
        binding.rvHistory.gone()
        binding.emptyLayout.clEmpty.visible()
        binding.emptyLayout.tvEmptyDescription.text = msg
    }

    private fun obtainViewModel(activity: AppCompatActivity): HistoryViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[HistoryViewModel::class.java]
    }

    override fun onItemClicked(historyAudioEntity: HistoryAudioEntity) {
        startActivity(Intent(this, ResultPredictActivity::class.java).apply {
            putExtra(PARAM, PAR2)
            putExtra(HISTORY_AUDIO_ENTITY, historyAudioEntity)
        })
    }
}