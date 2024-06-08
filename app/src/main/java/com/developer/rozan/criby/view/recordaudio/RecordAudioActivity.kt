package com.developer.rozan.criby.view.recordaudio

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.developer.rozan.criby.R
import com.developer.rozan.criby.databinding.ActivityRecordAudioBinding
import com.developer.rozan.criby.utils.getDate
import com.developer.rozan.criby.utils.showToast
import com.developer.rozan.criby.view.chatbot.ChatbotActivity
import java.io.IOException

class RecordAudioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordAudioBinding

    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var mediaPlayer: MediaPlayer

    private var dirPath = ""
    private var fileName = ""
    private var filePath = ""
    private var isRecording = false

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast(this, getString(R.string.recorder_permission_request_approved))
            } else {
                showToast(this, getString(R.string.recorder_permission_request_denied))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordAudioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnChatbot.setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java))
        }

        binding.btnRecordAudio.setOnClickListener {
            if (!audioRecorderPermissionsGranted(this)) {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            } else {
                if (!isRecording) {
                    startRecording()
                } else {
                    stopRecording()
                }
            }
        }

        binding.btnPutarSuara.setOnClickListener {
            preparePlayingSound()
        }
    }

    private fun startRecording() {
        mediaRecorder = MediaRecorder()

        dirPath = "${externalCacheDir?.absolutePath}/"
        fileName = "audio_record_${getDate()}"
        filePath = "$dirPath$fileName.mp3"

        mediaRecorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(filePath)

            try {
                prepare()
            } catch (e: IOException) {
                showToast(this@RecordAudioActivity, e.message.toString())
            }

            start()
        }

        Glide.with(this).load(R.drawable.img_record).into(binding.btnRecordAudio)
        isRecording = true
    }

    private fun stopRecording() {
        mediaRecorder.apply {
            stop()
            release()
        }

        Glide.with(this).load(R.drawable.img_record_audio).into(binding.btnRecordAudio)
        isRecording = false
    }

    private fun preparePlayingSound() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.apply {
            setDataSource(filePath)
            prepare()
        }

        setStartStopPlaying()
    }

    private fun setStartStopPlaying() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            binding.btnPutarSuara.text = "Berhenti Putar Ulang Suara"
        } else {
            mediaPlayer.stop()
            binding.btnPutarSuara.text = "Putar Ulang Suara"
        }
    }

    private fun audioRecorderPermissionsGranted(activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }
}