package com.developer.rozan.criby.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.developer.rozan.criby.R
import com.developer.rozan.criby.databinding.ActivityHomeBinding
import com.developer.rozan.criby.view.recordaudio.RecordAudioActivity
import com.developer.rozan.criby.view.uploadaudio.UploadAudioActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRecordAudio.setOnClickListener {
            startActivity(Intent(this, RecordAudioActivity::class.java))
        }

        binding.btnUploadAudio.setOnClickListener {
            startActivity(Intent(this, UploadAudioActivity::class.java))
        }
    }
}