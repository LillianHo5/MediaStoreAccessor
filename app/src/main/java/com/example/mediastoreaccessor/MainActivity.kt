package com.example.mediastoreaccessor

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.appsearch.SetSchemaRequest.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest


class MainActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Request the permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE)
        } else {
            // Permission has already been granted
            var audioFileAccessor = AudioFileAccessor()
            var audioFiles = audioFileAccessor.getAudioFiles(contentResolver)
            var audioTextView = findViewById<TextView>(R.id.audioFileTextView)
            Log.i("Audio File Size:", audioFiles.size.toString())
            var audioFileText = ""
            if (audioFiles.isEmpty()) {
                audioTextView.text = "No audio files found in the media store."
            } else {
                for (audioFile in audioFiles) {
                    audioFileText += audioFile.data + "/n "
                }
                audioTextView.text = audioFileText
            }
        }
    }

}