package com.bukin.androidprofessionallevel.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bukin.androidprofessionallevel.R
import com.bukin.androidprofessionallevel.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var llShopList: LinearLayout
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llShopList = findViewById(R.id.ll_shop_list)
        // создаем ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // подписываемся на LiveData
        viewModel.shopList.observe(this) {
            showList(it)
        }
    }

    private fun showList(list: List<ShopItem>) {
        /*
        Реализация RecyclerView на базовых типах LinearLayout и Scroll view
        Реализация заключается в добавлении элементов списка в макет через addView()

        Для преобразования кода в xml используется LayoutInflater
        * */
        // Т.к. список дублируется при обновлении, нужно затереть сторое
        llShopList.removeAllViews()
        for (shopItem in list) {
            // Получаем макет
            val layoutId = if (shopItem.enabled) {
                R.layout.item_shop_enabled
            } else {
                R.layout.item_shop_disabled
            }
            // берем из текущей Activity и наполняем layout
            val view = LayoutInflater.from(this)
                .inflate(layoutId, llShopList, false)

            // Для отображения текста следует вызывать id текста для каждой View
            val tvName = view.findViewById<TextView>(R.id.tv_name)
            val tvCount = view.findViewById<TextView>(R.id.tv_count)
            tvName.text = shopItem.name
            tvCount.text = shopItem.count.toString()

            // Меняем статус активности у элемента списка
            view.setOnLongClickListener {
                viewModel.changeEnableState(shopItem)
                true
            }

            /*
            * Чтобы элементы не уходили в конец списка, можно их заранее отсортировать,
            * заменив список на TreeSet<ShopItem> в ShopListRepositoryImpl
            * */

            // Добавляем ко View
            llShopList.addView(view)
        }
    }
}