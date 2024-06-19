package com.developer.rozan.criby.audio

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import com.developer.rozan.criby.utils.addWavHeader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AndroidAudioRecorder(
    private val context: Context
) : AudioRecorder {

    private var recorder: MediaRecorder? = null
    private var audioRecord: AudioRecord? = null
    private val sampleRate = 44100

    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()
    }

    @SuppressLint("MissingPermission")
    fun recordAudioAndSaveAsWav(outputFilePath: String) {
        val channelConfig = AudioFormat.CHANNEL_IN_MONO
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT
        val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize
        )
        val audioData = ByteArray(bufferSize)

        audioRecord?.startRecording()

        val recordingThread = Thread {
            try {
                FileOutputStream(outputFilePath).use { outputStream ->
                    while (true) {
                        val read = audioRecord?.read(audioData, 0, bufferSize)
                        if (read == AudioRecord.ERROR_INVALID_OPERATION || read == AudioRecord.ERROR_BAD_VALUE) {
                            break
                        }
                        if (read != null) {
                            outputStream.write(audioData, 0, read)
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        recordingThread.start()
    }

    fun stopRecordAudioAndSaveAsWav(outputFilePath: String) {
        audioRecord?.stop()
        audioRecord?.release()

        // Add WAV header
        addWavHeader(File(outputFilePath), sampleRate, 1, 16)
    }

    override fun start(outputFile: String) {
        try {
            createRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(outputFile)

                prepare()
                start()

                recorder = this
            }
        } catch (io: IOException) {
            Log.d("HAHA", io.printStackTrace().toString())
        }
    }

    override fun stop() {
        recorder?.stop()
        recorder?.reset()
        recorder = null
    }
}