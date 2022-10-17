package com.bukin.androidprofessionallevel.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bukin.androidprofessionallevel.R
import com.bukin.androidprofessionallevel.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged() // обновление данных
        }

    // Как создать View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        // Создаем необходимое количество элементов на экране +
        // несколько сверху и снизу
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_shop_disabled,
                parent,
                false
            )
        return ShopItemViewHolder(view)
    }

    // Как вставить значения во View
    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        val status = if (shopItem.enabled) {
            "Active"
        } else {
            "Not active"
        }
        holder.view.setOnClickListener {
            true
        }
        /*
        * Т.к. RV создает только определенное количество элементов на экране,
        * то при скролле выподающие элементы переносятся на другую сторону,
        * но заполнение этих элементов происходит по новой логике, а старые
        * данные никуда не исчезают
        * */
        if (shopItem.enabled) {
            holder.tvName.text = "${shopItem.name} $status"
            holder.tvCount.text = shopItem.count.toString()
            holder.tvName.setTextColor(
                ContextCompat.getColor(
                    holder.view.context,
                    android.R.color.holo_red_light
                )
            )
            // Solution1
//        } else {
//            holder.tvName.text = ""
//            holder.tvCount.text = ""
//            holder.tvName.setTextColor(
//                ContextCompat.getColor(
//                    holder.view.context,
//                    android.R.color.white
//                )
//            )
        }
    }


    // Solution2
    // Вызывается, когда ViewHolder хотят переиспользовать
    // (например при появлении новых элементов при скроелле)
    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
    }

    // Возвращает тип view по его позиции (viewType)
    // viewType использвутся, когда в RV помещены разные макеты (itemShop enabled/disabled)
    // Плохое решение, т.к. позиция будет передаваться каждый раз при появлении элемента (медленно)
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }

    override fun getItemCount(): Int =
        shopList.size


    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }
}