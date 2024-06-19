package com.developer.rozan.criby.view.detailcrybaby

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import com.bumptech.glide.Glide
import com.developer.rozan.criby.R
import com.developer.rozan.criby.data.local.entity.CryBabyEntity
import com.developer.rozan.criby.databinding.ActivityDetailCryBabyBinding
import com.developer.rozan.criby.utils.CRY_BABY_ENTITY

class DetailCryBabyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCryBabyBinding

    private var cryBabyEntity: CryBabyEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCryBabyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getParcelableExtra<Parcelable>(CRY_BABY_ENTITY) != null)
            cryBabyEntity = intent.getParcelableExtra(CRY_BABY_ENTITY)

        binding.tvTitle.text = cryBabyEntity?.title
        binding.tvDescCryBaby.text = cryBabyEntity?.description
        Glide.with(binding.ivImageCryBaby)
            .load(cryBabyEntity?.image)
            .placeholder(R.drawable.img_mom_with_baby)
            .error(R.drawable.img_mom_with_baby)
            .into(binding.ivImageCryBaby)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnReference.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cryBabyEntity?.reference))
            startActivity(intent)
        }
    }
}