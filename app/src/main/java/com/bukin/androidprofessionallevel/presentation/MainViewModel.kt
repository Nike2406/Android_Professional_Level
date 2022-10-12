package com.bukin.androidprofessionallevel.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bukin.androidprofessionallevel.data.ShopListRepositoryImpl // нарушение зависимостей
import com.bukin.androidprofessionallevel.domain.ShopItem
import com.bukin.androidprofessionallevel.domain.useCase.DeleteShopItemUseCase
import com.bukin.androidprofessionallevel.domain.useCase.EditShopItemUseCase
import com.bukin.androidprofessionallevel.domain.useCase.GetShopListUseCase

class MainViewModel : ViewModel() { // т.к. не нужен контекст (AndroidViewModel(context) )

    // DI in future
    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    /*
    * Взаимодействие Activity и ViewModel должно происходить через LiveData()
    * для сохранения данных при уничтожении activity
    * */
    // Наследник LiveData
    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newShopItem = ShopItem(
            name = shopItem.name,
            count = shopItem.count,
            enabled = !shopItem.enabled,
            id = shopItem.id
        )
        // else val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newShopItem)
    }
}