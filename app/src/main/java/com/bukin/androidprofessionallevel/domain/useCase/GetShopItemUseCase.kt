package com.bukin.androidprofessionallevel.domain.useCase

import com.bukin.androidprofessionallevel.domain.ShopItem
import com.bukin.androidprofessionallevel.domain.repository.ShopListRepository

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItem(shopItemId: Int) : ShopItem {
        return shopListRepository.getShopItem(shopItemId)
    }
}