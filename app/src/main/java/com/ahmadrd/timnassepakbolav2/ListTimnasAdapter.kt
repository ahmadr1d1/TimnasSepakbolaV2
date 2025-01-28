package com.ahmadrd.timnassepakbolav2

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.timnassepakbolav2.databinding.ItemRowTimnasBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListTimnasAdapter(private val listTimnas: ArrayList<Timnas>):
    RecyclerView.Adapter<ListTimnasAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(var binding: ItemRowTimnasBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowTimnasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listTimnas.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, desc, photo) = listTimnas[position]
        Glide.with(holder.itemView.context)
            .load(photo)
//            .apply(RequestOptions().override(800, 600))
            .into(holder.binding.imgPhoto)
        holder.binding.tvName.text = name
        holder.binding.tvDesc.text = desc

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("DETAIL", listTimnas[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Timnas)
    }
}