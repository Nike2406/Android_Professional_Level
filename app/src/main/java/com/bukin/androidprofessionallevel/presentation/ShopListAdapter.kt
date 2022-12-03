package com.bukin.androidprofessionallevel.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.bukin.androidprofessionallevel.R
import com.bukin.androidprofessionallevel.databinding.ItemShopDisabledBinding
import com.bukin.androidprofessionallevel.databinding.ItemShopEnabledBinding
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
        /*
        * С помощью DataBindingUtil можно определить необходимые layout
        * */
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            shopItemLayout, // Передаем id layout, который будем использовать
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    // Как вставить значения во View
    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        // Для получения элемента из списка используется служебный метод getItem()
        val shopItem = getItem(position)
        val binding = holder.binding
        with(binding){
            root.setOnLongClickListener {
                onShopItemLongCLickListener?.invoke(shopItem)
                true
            }
            root.setOnClickListener {
                onShopItemClickListener?.invoke(shopItem)
            }
            when (binding) {
                is ItemShopDisabledBinding -> {
                    binding.shopItem = shopItem
                }
                is ItemShopEnabledBinding -> {
                    binding.shopItem = shopItem
                }
            }
        }
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