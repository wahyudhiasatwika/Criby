package com.developer.rozan.criby.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developer.rozan.criby.R
import com.developer.rozan.criby.data.local.entity.CryBabyEntity
import com.developer.rozan.criby.databinding.ItemCryBabyBinding
import com.developer.rozan.criby.listener.OnCryBabyClickListener

class HomeAdapter(private val cryBabyList: List<CryBabyEntity>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var listener: OnCryBabyClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCryBabyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cryBaby = cryBabyList[position]
        holder.bind(cryBaby)

        holder.cvCryBaby.setOnClickListener {
            listener?.onItemClicked(cryBaby)
        }
    }

    override fun getItemCount(): Int {
        return cryBabyList.size
    }

    class ViewHolder(private val view: ItemCryBabyBinding) : RecyclerView.ViewHolder(view.root) {

        val cvCryBaby = view.cvCryBaby
        fun bind(cryBabyEntity: CryBabyEntity) {
            view.tvTitleBaby.text = cryBabyEntity.title
            Glide.with(view.ivImagaBaby)
                .load(cryBabyEntity.image)
                .placeholder(R.drawable.img_mom_with_baby)
                .error(R.drawable.img_mom_with_baby)
                .into(view.ivImagaBaby)
        }
    }
}