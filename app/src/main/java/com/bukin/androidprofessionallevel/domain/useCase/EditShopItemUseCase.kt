package com.bukin.androidprofessionallevel.domain.useCase

import com.bukin.androidprofessionallevel.domain.ShopItem
import com.bukin.androidprofessionallevel.domain.repository.ShopListRepository

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}