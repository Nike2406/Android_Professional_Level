package com.bukin.androidprofessionallevel.domain.useCase

import com.bukin.androidprofessionallevel.domain.ShopItem
import com.bukin.androidprofessionallevel.domain.repository.ShopListRepository

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList(): List<ShopItem> {
        return shopListRepository.getShopList()
    }
}