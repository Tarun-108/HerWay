package com.taruns.herway.navigationdrawer

import ai.picovoice.porcupine.*
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.CalendarContract.Colors
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.taruns.herway.R
import com.taruns.herway.databinding.ActivityTriggerBinding
import java.util.*

class TriggerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTriggerBinding
    private val ACCESS_KEY = "NerZvzfdFJoIqsfoaFqFJHTPlMdKLpfSHvVsz+p/GLuHlFPI8xNz6w=="
    private var porcupineManager: PorcupineManager? = null
    private var notificationPlayer: MediaPlayer? = null


    private val porcupineManagerCallback: PorcupineManagerCallback =
        PorcupineManagerCallback {
            runOnUiThread {
                if (!notificationPlayer!!.isPlaying) {
                    notificationPlayer!!.start()
                }
                val detectedBackgroundColor = ContextCompat.getColor(
                    applicationContext,
                    R.color.colorAccent
                )

                binding.layout.setBackgroundColor(detectedBackgroundColor)
                object : CountDownTimer(1000, 100) {
                    override fun onTick(millisUntilFinished: Long) {
                        if (!notificationPlayer!!.isPlaying) {
                            notificationPlayer!!.start()
                        }
                    }

                    override fun onFinish() {
                        binding.layout.setBackgroundColor(resources.getColor(R.color.white))
                    }
                }.start()
            }
        }

    private fun startPorcupine() {
        try {
            val keywordName = binding.spinnerKeyword.selectedItem.toString()
            val keyword = keywordName.lowercase(Locale.getDefault()).replace(" ", "_") + ".ppn"
            porcupineManager = PorcupineManager.Builder()
                .setAccessKey(ACCESS_KEY)
                .setKeywordPath(keyword)
                .setSensitivity(0.7f)
                .build(applicationContext, porcupineManagerCallback)
            porcupineManager!!.start()
        } catch (e: PorcupineInvalidArgumentException) {
            onPorcupineInitError(
                java.lang.String.format(
                    "%s\nEnsure your accessKey '%s' is a valid access key.",
                    e.message,
                    ACCESS_KEY
                )
            )
        } catch (e: PorcupineActivationException) {
            onPorcupineInitError("AccessKey activation error")
        } catch (e: PorcupineActivationLimitException) {
            onPorcupineInitError("AccessKey reached its device limit")
        } catch (e: PorcupineActivationRefusedException) {
            onPorcupineInitError("AccessKey refused")
        } catch (e: PorcupineActivationThrottledException) {
            onPorcupineInitError("AccessKey has been throttled")
        } catch (e: PorcupineException) {
            onPorcupineInitError("Failed to initialize Porcupine " + e.message)
        }
    }

    private fun stopPorcupine() {
        if (porcupineManager != null) {
            try {
                porcupineManager!!.stop()
                porcupineManager!!.delete()
            } catch (e: PorcupineException) {
                displayError("Failed to stop Porcupine.")
            }
        }
    }

    private fun onPorcupineInitError(errorMessage: String) {
        runOnUiThread {
            val errorText = findViewById<TextView>(R.id.errorMessage)
            errorText.text = errorMessage
            errorText.visibility = View.VISIBLE
            val recordButton = findViewById<ToggleButton>(R.id.record_button)
            recordButton.background = ContextCompat.getDrawable(
                applicationContext,
                R.drawable.button_disabled
            )
            recordButton.isChecked = false
            recordButton.isEnabled = false
        }
    }

    private fun displayError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTriggerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notificationPlayer = MediaPlayer.create(this, R.raw.notification)
        configureKeywordSpinner()
    }

    private fun configureKeywordSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.keywords,
            R.layout.keyword_spinner_item
        )
        adapter.setDropDownViewResource(R.layout.keyword_spinner_item)
        binding.spinnerKeyword.adapter = adapter
        val recordButton = findViewById<ToggleButton>(R.id.record_button)
        binding.spinnerKeyword.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                if (recordButton.isChecked) {
                    stopPorcupine()
                    recordButton.toggle()
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing.
            }
        }
    }


    override fun onStop() {
        val recordButton = findViewById<ToggleButton>(R.id.record_button)
        recordButton.isChecked = false
        super.onStop()
    }

    private fun hasRecordPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun requestRecordPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 0)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size == 0 || grantResults[0] == PackageManager.PERMISSION_DENIED) {
            onPorcupineInitError("Microphone permission is required for this demo")
        } else {
            startPorcupine()
        }
    }

    fun process(view: View?) {
        val recordButton = findViewById<ToggleButton>(R.id.record_button)
        if (recordButton.isChecked) {
            if (hasRecordPermission()) {
                startPorcupine()
            } else {
                requestRecordPermission()
            }
        } else {
            stopPorcupine()
        }
    }
}