package com.bukin.androidprofessionallevel.domain.repository

import com.bukin.androidprofessionallevel.domain.ShopItem

interface ShopListRepository {
    fun addShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun getShopItem(shopItemId: Int) : ShopItem
    fun getShopList(): List<ShopItem>
}