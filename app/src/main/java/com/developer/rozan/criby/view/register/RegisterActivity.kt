package com.developer.rozan.criby.view.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.developer.rozan.criby.R
import com.developer.rozan.criby.databinding.ActivityRegisterBinding
import com.developer.rozan.criby.utils.DELAY_1500L
import com.developer.rozan.criby.utils.closeKeyboard
import com.developer.rozan.criby.utils.isValidEmail
import com.developer.rozan.criby.utils.showKeyboard
import com.developer.rozan.criby.utils.showSnackBar
import com.developer.rozan.criby.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                textWatchers()
            }
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnLogin.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            val fullName = binding.tfFullname.editText?.text.toString()
            val email = binding.tfEmail.editText?.text.toString()
            val password = binding.tfPassword.editText?.text.toString()
            val confirmPassword = binding.tfConfirmPassword.editText?.text.toString()

            if (validateRegister(fullName, email, password, confirmPassword)) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showSnackBar(
                            this@RegisterActivity,
                            resources.getString(R.string.account_created)
                        )
                        saveUserData(fullName)
                    }
                }.addOnFailureListener {
                    showSnackBar(
                        this@RegisterActivity,
                        it.localizedMessage?.toString() ?: "Gagal membuat akun baru."
                    )
                }
            }
        }
    }

    private fun saveUserData(fullName: String) {
        val user = auth.currentUser ?: return
        val userData = mapOf(
            "name" to fullName
        )

        firestore.collection("users").document(user.uid)
            .set(userData)
            .addOnSuccessListener {
                auth.signOut()
                lifecycleScope.launch {
                    delay(DELAY_1500L)
                    finish()
                }
            }
            .addOnFailureListener {
                showToast(
                    this,
                    it.localizedMessage?.toString() ?: "Gagal menambahkan user name."
                )
            }
    }

    private fun validateRegister(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        closeKeyboard(this, binding.btnRegister)

        return when {
            fullName.isEmpty() -> {
                binding.tfFullname.error = resources.getString(R.string.full_name_must_field)
                binding.tfFullname.requestFocus()
                showKeyboard(this, binding.tfFullname.editText!!)
                false
            }

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

            confirmPassword.isEmpty() -> {
                binding.tfConfirmPassword.error =
                    resources.getString(R.string.confirm_passowrd_must_field)
                binding.tfConfirmPassword.requestFocus()
                showKeyboard(this, binding.tfConfirmPassword.editText!!)
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

            password != confirmPassword -> {
                binding.tfConfirmPassword.error = resources.getString(R.string.match_password)
                binding.tfConfirmPassword.requestFocus()
                showKeyboard(this, binding.tfConfirmPassword.editText!!)
                false
            }

            !binding.cbTermConditions.isChecked -> {
                showSnackBar(this, resources.getString(R.string.term_and_condition_check))
                binding.cbTermConditions.requestFocus()
                false
            }

            else -> {
                binding.tfFullname.isErrorEnabled = false
                binding.tfEmail.isErrorEnabled = false
                binding.tfPassword.isErrorEnabled = false
                binding.tfConfirmPassword.isErrorEnabled = false
                true
            }
        }
    }

    private fun textWatchers() {
        binding.tfFullname.editText?.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isNotEmpty() -> binding.tfFullname.isErrorEnabled = false
            }
        }

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

        binding.tfConfirmPassword.editText?.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isNotEmpty() -> binding.tfConfirmPassword.isErrorEnabled = false
            }
        }
    }
}