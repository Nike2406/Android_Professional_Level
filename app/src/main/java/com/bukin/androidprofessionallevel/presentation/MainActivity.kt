package com.bukin.androidprofessionallevel.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bukin.androidprofessionallevel.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        // создаем ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // подписываемся на LiveData
        viewModel.shopList.observe(this) {
            // Вставляем новый список в RV
            shopListAdapter.shopList = it
        }
    }

    // метод настройки RecyclerView
    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        shopListAdapter = ShopListAdapter()
        with(rvShopList) {
        adapter = shopListAdapter
            // Устанавливаем максимальный размер пула элементов RV
            // 1 - для какого элемента, 2 - мах размер
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
    }
}