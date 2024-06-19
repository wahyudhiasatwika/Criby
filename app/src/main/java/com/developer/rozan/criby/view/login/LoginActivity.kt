package com.developer.rozan.criby.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.developer.rozan.criby.R
import com.developer.rozan.criby.databinding.ActivityLoginBinding
import com.developer.rozan.criby.utils.DELAY_1500L
import com.developer.rozan.criby.utils.closeKeyboard
import com.developer.rozan.criby.utils.isValidEmail
import com.developer.rozan.criby.utils.showKeyboard
import com.developer.rozan.criby.utils.showSnackBar
import com.developer.rozan.criby.view.forgotpassword.ForgotPasswordActivity
import com.developer.rozan.criby.view.home.HomeActivity
import com.developer.rozan.criby.view.register.RegisterActivity
import com.developer.rozan.criby.view.welcome.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                textWatchers()
            }
        }

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }

        binding.btnForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.tfEmail.editText?.text.toString()
            val password = binding.tfPassword.editText?.text.toString()

            if (validateLogin(email, password)) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        lifecycleScope.launch {
                            showSnackBar(
                                this@LoginActivity,
                                resources.getString(R.string.login_successful)
                            )
                            delay(DELAY_1500L)
                            toHomeActivity()
                            finish()
                        }
                    }
                }.addOnFailureListener {
                    showSnackBar(
                        this@LoginActivity,
                        it.localizedMessage?.toString() ?: "Gagal masuk."
                    )
                }
            }
        }
    }

    private fun toHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun validateLogin(email: String, password: String): Boolean {
        closeKeyboard(this, binding.btnLogin)

        return when {
            email.isEmpty() -> {
                binding.tfEmail.error = resources.getString(R.string.email_must_field)
                binding.tfEmail.requestFocus()
                showKeyboard(this, binding.tfEmail.editText!!)
                false
            }

            password.isEmpty() -> {
                binding.tfPassword.error = resources.getString(R.string.password_must_field)
                binding.tfPassword.requestFocus()
                showKeyboard(this, binding.tfPassword.editText!!)
                false
            }

            !isValidEmail(email) -> {
                binding.tfEmail.error = resources.getString(R.string.valid_email)
                binding.tfEmail.requestFocus()
                showKeyboard(this, binding.tfEmail.editText!!)
                false
            }

            password.length <= 7 -> {
                binding.tfPassword.error = resources.getString(R.string.valid_password, 8)
                binding.tfPassword.requestFocus()
                showKeyboard(this, binding.tfPassword.editText!!)
                false
            }

            else -> {
                binding.tfEmail.isErrorEnabled = false
                binding.tfPassword.isErrorEnabled = false
                true
            }
        }
    }

    private fun textWatchers() {
        binding.tfEmail.editText?.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isNotEmpty() -> binding.tfEmail.isErrorEnabled = false
            }
        }

        binding.tfPassword.editText?.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isNotEmpty() -> binding.tfPassword.isErrorEnabled = false
            }
        }
    }
}