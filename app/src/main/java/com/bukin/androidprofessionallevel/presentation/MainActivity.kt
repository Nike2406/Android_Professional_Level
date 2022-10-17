package com.bukin.androidprofessionallevel.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bukin.androidprofessionallevel.R
import com.bukin.androidprofessionallevel.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        // создаем ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // подписываемся на LiveData
        viewModel.shopList.observe(this) {
            // Вставляем новый список в RV
            adapter.shopList = it
        }
    }

    // метод настройки RecyclerView
    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = ShopListAdapter()
        rvShopList.adapter = adapter
    }
}