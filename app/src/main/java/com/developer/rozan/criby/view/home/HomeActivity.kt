package com.developer.rozan.criby.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.developer.rozan.criby.data.local.entity.CryBabyEntity
import com.developer.rozan.criby.databinding.ActivityHomeBinding
import com.developer.rozan.criby.listener.OnCryBabyClickListener
import com.developer.rozan.criby.utils.CRY_BABY_ENTITY
import com.developer.rozan.criby.utils.listBabyCries
import com.developer.rozan.criby.utils.showSnackBar
import com.developer.rozan.criby.view.detailcrybaby.DetailCryBabyActivity
import com.developer.rozan.criby.view.history.HistoryActivity
import com.developer.rozan.criby.view.recordaudio.RecordAudioActivity
import com.developer.rozan.criby.view.splashscreen.SplashScreenActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity(), OnCryBabyClickListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private val homeAdapter by lazy {
        HomeAdapter(listBabyCries)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                initRecyclerView()
            }
        }

        binding.ivHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        binding.ivLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        binding.btnRecordAudio.setOnClickListener {
            startActivity(Intent(this, RecordAudioActivity::class.java))
        }

        binding.btnUploadAudio.setOnClickListener {
            showSnackBar(this, "Fitur sedang dikembangkan.")
            //startActivity(Intent(this, UploadAudioActivity::class.java))
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Yes") { _, _ ->
                signOut()
                toSplashScreen()
                finish()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun signOut() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val credentialManager = CredentialManager.create(this@HomeActivity)
                auth.signOut()
                credentialManager.clearCredentialState(ClearCredentialStateRequest())
            }
        }
    }

    private fun toSplashScreen() {
        startActivity(Intent(this, SplashScreenActivity::class.java))
    }

    private fun initRecyclerView() {
        binding.rvCryBaby.apply {
            layoutManager = GridLayoutManager(applicationContext, 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val itemCount = homeAdapter.itemCount
                        return if (itemCount % 2 == 1 && position == itemCount - 1) {
                            2 // Make the last item span 2 columns if the total count is odd
                        } else {
                            1 // Default span size
                        }
                    }
                }
            }
            setHasFixedSize(true)
            adapter = homeAdapter
            homeAdapter.listener = this@HomeActivity
        }
    }

    override fun onItemClicked(cryBabyEntity: CryBabyEntity) {
        startActivity(Intent(this, DetailCryBabyActivity::class.java).apply {
            putExtra(CRY_BABY_ENTITY, cryBabyEntity)
        })
    }
}