package com.bukin.androidprofessionallevel.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bukin.androidprofessionallevel.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    // Добавляем контейнер для горизонтальной ориентации
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shopItemContainer = findViewById(R.id.shop_item_container)
        setupRecyclerView()
        // создаем ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // подписываемся на LiveData
        viewModel.shopList.observe(this) {
            // Вставляем новый список в RV через ListAdapter.submitList(list)
            shopListAdapter.submitList(it)
        }

        /*
        * Для перехода между activity используется putExtra/getExtra
        * с использованием интентов
        * */
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }



    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode(): Boolean {
        return shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        // удаляем один фрагмент из backstack
        // тем самым перед добавлением нового фрагмента, старый будет удален
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            // добавляем в стек экранов (backstack), можно передать имя фрагмента
            .addToBackStack(null)
            .commit()
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
            if (isOnePaneMode()) {
                /*
               * При клике на экран будет запущен режим редактирования
               * */
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }

        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongCLickListener = {
            viewModel.changeEnableState(it)
        }
    }
}