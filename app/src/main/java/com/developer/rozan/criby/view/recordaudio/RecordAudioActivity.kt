package com.developer.rozan.criby.view.recordaudio

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.developer.rozan.criby.R
import com.developer.rozan.criby.audio.AndroidAudioRecorder
import com.developer.rozan.criby.data.factory.ViewModelFactory
import com.developer.rozan.criby.data.remote.response.BaseResponse
import com.developer.rozan.criby.data.remote.response.PredictionResponse
import com.developer.rozan.criby.databinding.ActivityRecordAudioBinding
import com.developer.rozan.criby.utils.AUDIO_FILEPATH
import com.developer.rozan.criby.utils.DELAY_2000L
import com.developer.rozan.criby.utils.DESCRIPTION
import com.developer.rozan.criby.utils.PAR1
import com.developer.rozan.criby.utils.PARAM
import com.developer.rozan.criby.utils.PREDICTION_RESPONSE
import com.developer.rozan.criby.utils.getDate
import com.developer.rozan.criby.utils.showSnackBar
import com.developer.rozan.criby.utils.showToast
import com.developer.rozan.criby.view.resultpredict.DescriptionUiState
import com.developer.rozan.criby.view.resultpredict.ResultPredictActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class RecordAudioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordAudioBinding
    private lateinit var recordAudioViewModel: RecordAudioViewModel
    private lateinit var predictionResponse: PredictionResponse

    private val mediaRecorder by lazy {
        AndroidAudioRecorder(applicationContext)
    }

    private var dirPath = ""
    private var fileName = ""
    private var filePath = ""
    private var isRecording = false

    private var desc = ""

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                startRecording()
            } else {
                showToast(this, getString(R.string.recorder_permission_request_denied))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordAudioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recordAudioViewModel = obtainViewModel(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                uploadSoundProcess()
                getDeskripsiProcess()
            }
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnRecordAudio.setOnClickListener {
            if (!audioRecorderPermissionsGranted(this)) {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            } else {
                if (!isRecording) {
                    startRecording()
                } else {
                    stopRecording()
                    prepareUploadSound()
                }
            }
        }
    }

    private fun prepareUploadSound() {
        val requestSoundFile = File(filePath).asRequestBody("audio/wav".toMediaTypeOrNull())

        val soundMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file",
            filePath,
            requestSoundFile
        )

        recordAudioViewModel.uploadSound(soundMultipart)
    }

    private fun uploadSoundProcess() {
        recordAudioViewModel.resultUploadSound.observe(this) {
            when (it) {
                is BaseResponse.Success<*> -> {
                    predictionResponse = it.data as PredictionResponse
                    //showLoading(false)
                    showSnackBar(this, "Berhasil upload file.")
                    lifecycleScope.launch {
                        recordAudioViewModel.getDescPrediksi(predictionResponse)
                    }
                }

                is BaseResponse.Error -> {
                    showSnackBar(this, it.exception.message.toString())
                    //showLoading(false)
                }

                is BaseResponse.Loading -> {
                    //showLoading(it.isLoading)
                }
            }
        }
    }

    private fun getDeskripsiProcess() {
        recordAudioViewModel.uiState.observe(this) {
            when (it) {
                is DescriptionUiState.Success -> {
                    desc = it.outputText
                    lifecycleScope.launch {
                        delay(DELAY_2000L)
                        toResultPredict(predictionResponse, desc)
                    }
                }

                is DescriptionUiState.Error -> {
                    showSnackBar(this, it.errorMessage)
                    //showLoading(false)
                }

                else -> {

                }
            }
        }
    }

    private fun toResultPredict(predictionResponse: PredictionResponse, desc: String) {
        val intent = Intent(this, ResultPredictActivity::class.java).apply {
            putExtra(PARAM, PAR1)
            putExtra(AUDIO_FILEPATH, filePath)
            putExtra(PREDICTION_RESPONSE, predictionResponse)
            putExtra(DESCRIPTION, desc)
        }
        startActivity(intent)
        finish()
    }

    private fun startRecording() {
        isRecording = true
        Glide.with(this).load(R.drawable.img_record).into(binding.btnRecordAudio)

        dirPath = "${externalCacheDir?.absolutePath}/"
        fileName = "audio_record_${getDate()}"
        filePath = "$dirPath$fileName.wav"

        mediaRecorder.recordAudioAndSaveAsWav(filePath)

        //mediaRecorder.start(filePath)
    }

    private fun stopRecording() {
        Glide.with(this).load(R.drawable.img_record_audio).into(binding.btnRecordAudio)
        isRecording = false

        mediaRecorder.stopRecordAudioAndSaveAsWav(filePath)

        //mediaRecorder.stop()
    }

    private fun audioRecorderPermissionsGranted(activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun obtainViewModel(activity: AppCompatActivity): RecordAudioViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[RecordAudioViewModel::class.java]
    }
}