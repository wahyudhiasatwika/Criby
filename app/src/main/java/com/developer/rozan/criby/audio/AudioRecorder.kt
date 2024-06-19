package com.developer.rozan.criby.audio

import java.io.File

interface AudioRecorder {
    fun start(outputFile: String)
    fun stop()
}