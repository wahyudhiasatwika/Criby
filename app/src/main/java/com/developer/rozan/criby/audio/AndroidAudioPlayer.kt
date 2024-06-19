package com.developer.rozan.criby.audio

import android.media.MediaPlayer

class AndroidAudioPlayer : AudioPlayer {

    private var player: MediaPlayer? = null

    var isPlay = false

    private fun createPlayer(): MediaPlayer {
        return MediaPlayer()
    }

    override fun playFile(file: String) {
        createPlayer().apply {
            setDataSource(file)
            prepare()
            start()

            player = this
            isPlay = true
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
        isPlay = false
    }
}