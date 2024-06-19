package com.developer.rozan.criby.audio

interface AudioRecorder {
    fun start(outputFile: String)
    fun stop()
}