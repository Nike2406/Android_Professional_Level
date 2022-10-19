package com.bukin.androidprofessionallevel.presentation

import androidx.recyclerview.widget.DiffUtil
import com.bukin.androidprofessionallevel.domain.ShopItem

class ShopListDiffCallback(
    private val oldList: List<ShopItem>,
    private val newList: List<ShopItem>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // у data классов equals and hashCode уже переопределны,
        // так что можно oldList[oldItemPosition] == newList[newItemPosition]
        return oldList[oldItemPosition].name == newList[newItemPosition].name &&
        oldList[oldItemPosition].count == newList[newItemPosition].count &&
        oldList[oldItemPosition].enabled == newList[newItemPosition].enabled
    }
}