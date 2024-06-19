package com.developer.rozan.criby.view.forgotpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.developer.rozan.criby.R
import com.developer.rozan.criby.databinding.ActivityForgotPasswordBinding
import com.developer.rozan.criby.utils.DELAY_1500L
import com.developer.rozan.criby.utils.closeKeyboard
import com.developer.rozan.criby.utils.isValidEmail
import com.developer.rozan.criby.utils.showKeyboard
import com.developer.rozan.criby.utils.showSnackBar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnSubmit.setOnClickListener {
            val email = binding.tfEmail.editText?.text.toString()

            if (validateForgot(email)) {
                sendPasswordResetEmail(email)
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun validateForgot(email: String): Boolean {
        closeKeyboard(this, binding.btnSubmit)

        return when {
            email.isEmpty() -> {
                binding.tfEmail.error = resources.getString(R.string.email_must_field)
                binding.tfEmail.requestFocus()
                showKeyboard(this, binding.tfEmail.editText!!)
                false
            }

            !isValidEmail(email) -> {
                binding.tfEmail.error = resources.getString(R.string.valid_email)
                binding.tfEmail.requestFocus()
                showKeyboard(this, binding.tfEmail.editText!!)
                false
            }

            else -> {
                binding.tfEmail.isErrorEnabled = false
                true
            }
        }
    }

    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                lifecycleScope.launch {
                    showSnackBar(this@ForgotPasswordActivity, "Silahkan periksa email Anda.")
                    delay(DELAY_1500L)
                    finish()
                }
            }
        }.addOnFailureListener {
            showSnackBar(this, it.localizedMessage?.toString() ?: "Ga bisa kirim email.")
        }
    }
}