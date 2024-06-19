package com.developer.rozan.criby.view.resultpredict

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PorterDuff
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.developer.rozan.criby.R
import com.developer.rozan.criby.data.factory.ViewModelFactory
import com.developer.rozan.criby.data.local.entity.HistoryAudioEntity
import com.developer.rozan.criby.data.remote.response.PredictionResponse
import com.developer.rozan.criby.databinding.ActivityResultPredictBinding
import com.developer.rozan.criby.utils.AUDIO_FILEPATH
import com.developer.rozan.criby.utils.DESCRIPTION
import com.developer.rozan.criby.utils.HISTORY_AUDIO_ENTITY
import com.developer.rozan.criby.utils.PAR1
import com.developer.rozan.criby.utils.PARAM
import com.developer.rozan.criby.utils.PREDICTION_RESPONSE
import com.developer.rozan.criby.utils.gone
import com.developer.rozan.criby.utils.showSnackBar
import com.developer.rozan.criby.utils.showToast
import com.developer.rozan.criby.utils.visible
import com.developer.rozan.criby.view.chatbot.ChatbotActivity

class ResultPredictActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultPredictBinding
    private lateinit var resultPredictViewModel: ResultPredictViewModel
    private lateinit var filePath: String
    private lateinit var desc: String

    private val mediaPlayer by lazy {
        MediaPlayer()
    }

    private lateinit var runnable: Runnable
    private lateinit var handler: Handler

    private var predictionResponse: PredictionResponse? = null
    private var historyAudioEntity: HistoryAudioEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultPredictViewModel = obtainViewModel(this)

        val param = intent.getIntExtra(PARAM, 0)

        if (param == PAR1) {
            if (intent.getParcelableExtra<Parcelable>(PREDICTION_RESPONSE) != null)
                predictionResponse = intent.getParcelableExtra(PREDICTION_RESPONSE)

            filePath = intent.getSerializableExtra(AUDIO_FILEPATH).toString()
            desc = intent.getSerializableExtra(DESCRIPTION).toString()

            binding.btnSaveResult.visible()
            binding.btnChatbot.visible()

            binding.tvResultAudio.text =
                "Predictions: ${predictionResponse?.predictions}\nConfidence: ${predictionResponse?.confidence}"
            binding.tvDescResultAudio.text = desc
        } else {
            if (intent.getParcelableExtra<Parcelable>(HISTORY_AUDIO_ENTITY) != null)
                historyAudioEntity = intent.getParcelableExtra(HISTORY_AUDIO_ENTITY)

            binding.btnSaveResult.gone()
            binding.btnChatbot.gone()

            filePath = historyAudioEntity?.audioUri ?: ""

            binding.tvResultAudio.text =
                "Predictions: ${historyAudioEntity?.klasifikasiPredict}\nConfidence: ${historyAudioEntity?.confidencePredict}"
            binding.tvDescResultAudio.text = historyAudioEntity?.descPredict
        }

        preparePlayingSound()

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnPlayAudio.setOnClickListener {
            if (filePath.isNotEmpty()) {
                if (!mediaPlayer.isPlaying) {
                    startPlaying()
                } else {
                    stopPlaying()
                }
            } else {
                showToast(this, "Ga ada audio yang diputar.")
            }
        }

        binding.btnSaveResult.setOnClickListener {
            predictionResponse?.let {
                saveResultHistory(filePath, it.predictions, it.confidence, desc)
            }
        }

        binding.btnChatbot.setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java))
        }
    }

    private fun startPlaying() {
        mediaPlayer.start()
        binding.btnPlayAudio.setImageResource(R.drawable.ic_pause)
        val color = ContextCompat.getColor(this, R.color.colorPrimary1)
        binding.btnPlayAudio.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        handler.postDelayed(runnable, 0)
    }

    private fun stopPlaying() {
        mediaPlayer.stop()
        binding.btnPlayAudio.setImageResource(R.drawable.ic_play)
        val color = ContextCompat.getColor(this, R.color.colorPrimary1)
        binding.btnPlayAudio.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        handler.removeCallbacks(runnable)
    }

    private fun saveResultHistory(
        filePath: String,
        klasifikasi: String,
        confidence: Double,
        desc: String
    ) {
        val resultHistoryEntity = HistoryAudioEntity(
            audioUri = filePath,
            klasifikasiPredict = klasifikasi,
            confidencePredict = confidence,
            descPredict = desc
        )

        resultPredictViewModel.insertResult(resultHistoryEntity)
        showSnackBar(this, "Hasil Analisis berhasil disimpan.")
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun preparePlayingSound() {
        mediaPlayer.apply {
            setDataSource(filePath)
            prepare()

            binding.progressAudio.max = duration
        }
        binding.progressAudio.setOnTouchListener { _, _ -> true }

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            binding.progressAudio.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable, 0)
        }

        mediaPlayer.setOnCompletionListener {
            binding.btnPlayAudio.setImageResource(R.drawable.ic_play)
            val color = ContextCompat.getColor(this, R.color.colorPrimary1)
            binding.btnPlayAudio.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            handler.removeCallbacks(runnable)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): ResultPredictViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[ResultPredictViewModel::class.java]
    }
}