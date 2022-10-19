package com.bukin.androidprofessionallevel.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
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
            // Вставляем новый список в RV через ListAdapter.submitList(list)
            shopListAdapter.submitList(it)
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
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rvShopList)
    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        /*
        * Реализация удаления элемента с помощью свайпа
        * Используем ItemTouchHelper в виде callback
        * Берем в методе onSwiped() у адаптера нужный элемент и удаляем его из ViewModel
        * После присоединияем это callback к RecyclerView
        * */
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                /*
                * Если нужно получить список элементов,
                * то можно вызвать ListAdapter.currentList(position)
                * */
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            Log.d(
                "MainActivity",
                "Item: id = ${it.id}, name = ${it.name}, count = ${it.count}, enabled = ${it.enabled}"
            )
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongCLickListener = {
            viewModel.changeEnableState(it)
        }
    }
}