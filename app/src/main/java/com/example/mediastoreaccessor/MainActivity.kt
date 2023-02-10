package com.example.mediastoreaccessor

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            accessAudioFiles()
        } else {
            // You can directly ask for the permission.
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                100
            )
        }
    }

    private fun accessAudioFiles() {
        var audioFileAccessor = AudioFileAccessor()
        var audioFiles = audioFileAccessor.getAudioFiles(contentResolver)
        var audioTextView = findViewById<TextView>(R.id.audioFileTextView)
        Log.i("Audio File Size:", audioFiles.size.toString())
        var audioFileText = ""
        if (audioFiles.isEmpty()) {
            audioTextView.text = "No audio files found in the media store."
        } else {
            for (audioFile in audioFiles) {
                audioFileText += audioFile.title + "\n "
            }
            audioTextView.text = audioFileText
        }
    }

    // https://developer.android.com/training/permissions/requesting
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    accessAudioFiles()
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the feature requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}