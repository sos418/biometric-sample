package com.example.myapplication

import android.R.string
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.Executor
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var biometricPromptManager: BiometricPromptManager

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        biometricPromptManager = BiometricPromptManager(this)

        val secureText = "Secure Text!"
        textView.text = secureText
        buttonEncrypt.setOnClickListener {
            biometricPromptManager.encryptPrompt(
                    data = secureText.toByteArray(),
                    failedAction = { showToast("encrypt failed") },
                    successAction = {
                        textView.text = String(it)
                        showToast("encrypt success")
                    }
            )
        }

        buttonDecrypt.setOnClickListener {
            biometricPromptManager.decryptPrompt(
                    failedAction = { showToast("decrypt failed") },
                    successAction = {
                        textView.text = String(it)
                        showToast("decrypt success")
                    }
            )
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}