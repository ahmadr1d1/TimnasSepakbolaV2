package com.ahmadrd.timnassepakbolav2

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ahmadrd.timnassepakbolav2.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dataTimnas = intent.getParcelableExtra("DETAIL", Timnas::class.java)
        Log.d("Detail Data", dataTimnas?.name.toString())

        if (dataTimnas != null) {
            binding.tvName.text = dataTimnas.name
            binding.tvDesc.text = dataTimnas.desc
            Glide.with(this)
                .load(dataTimnas.photo)
                .apply(RequestOptions().override(150, 200))
                .into(binding.imgDetail)
        }
    }
}