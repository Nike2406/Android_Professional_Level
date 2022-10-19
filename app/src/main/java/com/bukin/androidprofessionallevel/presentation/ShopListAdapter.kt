package com.bukin.androidprofessionallevel.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bukin.androidprofessionallevel.R
import com.bukin.androidprofessionallevel.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var count = 0
    var shopList = listOf<ShopItem>()
        set(value) {
            // Исправляем через DiffUtils
            // Проверяем, изменились ли элементы
            val callback = ShopListDiffCallback(shopList, value)
            // Производим вычисления, какие элементы изменились для адапетера
            // принимает callback
            // ! Работает в главном потоке, т.е. главный поток ждет пока закончится оп-ция
            val diffResult = DiffUtil.calculateDiff(callback)
            // Чтобы адаптер применил изменения, выполняем diffResult.dispatchUpdatesTo(adapter),
            // который уже вызовет необходимые методы (
            // notifyItemChanged(int), notifyItemInserted(int),
            // notifyItemRemoved(int), notifyItemRangeChanged(int, int),
            // notifyItemRangeInserted(int, int), notifyItemRangeRemoved(int, int))
            diffResult.dispatchUpdatesTo(this)
            // В конце присваиваем новое занчение через сеттер
            field = value
            /*
            * notifyDataSetChanged() - не поределяет, что именно изменилось в списке и
            * перерисовывает все
            notifyDataSetChanged() // обновление данных
            * */
        }
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
        /*
        Проблема: Адаптер привязывает все элменты при изменении состояния
        (notifyDataSetChanged() )
        Решение: Передавать список в адаптер для сравнения через DiffUtils,
        который будет определять изменения
        */
        Log.d("ADAPTER_ON_BIND", "onBindViewHolder created, count: ${++count}")
        val shopItem = shopList[position]
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
        val shopItem = shopList[position]

        return if (shopItem.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    override fun getItemCount(): Int =
        shopList.size


    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }


    companion object {

        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        const val MAX_POOL_SIZE = 15
    }
}