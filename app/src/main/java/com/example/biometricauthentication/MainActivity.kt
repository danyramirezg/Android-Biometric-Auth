package com.example.biometricauthentication

import android.hardware.biometrics.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.biometricauthentication.databinding.ActivityMainBinding
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo

    lateinit var executor: Executor
    lateinit var btnAut: Button
    lateinit var tvAuthStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //btnAut = binding.button
        //tvAuthStatus = binding.textViewAuthenticateStatus

        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = androidx.biometric.BiometricPrompt(
            this@MainActivity,
            executor,
            object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    binding.textViewAuthenticateStatus.text = "Error " + errString
                }
                override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    binding.textViewAuthenticateStatus.text = "Authentication is succesful"
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    binding.textViewAuthenticateStatus.text = "Authentication failed"
                }
    })
        // Setup title:
        promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint or face")
            .setNegativeButtonText("Cancel")
            .build()

        binding.button.setOnClickListener{
            biometricPrompt.authenticate(promptInfo)
        }
}
}