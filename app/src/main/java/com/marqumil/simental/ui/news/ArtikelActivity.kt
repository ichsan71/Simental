package com.marqumil.simental.ui.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marqumil.simental.R
import com.marqumil.simental.databinding.ActivityArtikelBinding
import com.marqumil.simental.remote.ResultState
import com.marqumil.simental.remote.response.ArticlesItem

class ArtikelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArtikelBinding
    private lateinit var adapter: ArtikelAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var artikelViewModel: ArtikelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.toolbarSubtitle.setOnClickListener{
            onBackPressed()
        }

        artikelViewModel = ArtikelViewModel()

        artikelViewModel.getArtikel()

        recyclerView = binding.rvBookmark
        recyclerView.layoutManager = LinearLayoutManager(this)

        artikelViewModel.ArtikelResponse.observe(this) {
            when (it) {
                is ResultState.Success -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    val artikelList = it
                    recyclerView.adapter = ArtikelAdapter(artikelList.value as List<ArticlesItem>)
                }
                is ResultState.Failure -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
                is ResultState.Loading -> {
                    //Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = android.view.View.VISIBLE
                }

                else -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}