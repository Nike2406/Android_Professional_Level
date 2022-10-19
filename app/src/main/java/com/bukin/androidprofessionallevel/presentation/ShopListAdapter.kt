package com.bukin.androidprofessionallevel.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bukin.androidprofessionallevel.R
import com.bukin.androidprofessionallevel.domain.ShopItem

/*
ListAdapter принимает два параметра:
    1 - Тип элемента, который отображает RV
    2 - Тип ViewHolder
в его конструктор нужно передать callback (ShopListDiffCallback())

Скрывает всю логику работы со списком
* */
class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongCLickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    // Как создать View getItemViewType()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        // Определяем View через
        val shopItemLayout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            else -> throw java.lang.RuntimeException("Unknown ViewType: $viewType")
        }

        // Создаем необходимое количество элементов на экране +
        // несколько сверху и снизу
        val view = LayoutInflater.from(parent.context)
            .inflate(
                shopItemLayout,
                parent,
                false
            )
        return ShopItemViewHolder(view)
    }

    // Как вставить значения во View
    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        // Для получения элемента из списка используется служебный метод getItem()
        val shopItem = getItem(position)
        holder.view.setOnLongClickListener {
            onShopItemLongCLickListener?.invoke(shopItem)
            true
        }
        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
            true
        }
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)

        return if (shopItem.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }


    companion object {

        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        const val MAX_POOL_SIZE = 15
    }
}