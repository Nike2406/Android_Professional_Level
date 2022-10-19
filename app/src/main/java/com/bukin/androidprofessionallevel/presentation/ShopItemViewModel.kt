package com.bukin.androidprofessionallevel.presentation

import androidx.lifecycle.ViewModel
import com.bukin.androidprofessionallevel.data.ShopListRepositoryImpl
import com.bukin.androidprofessionallevel.domain.ShopItem
import com.bukin.androidprofessionallevel.domain.useCase.AddShopItemUseCase
import com.bukin.androidprofessionallevel.domain.useCase.EditShopItemUseCase
import com.bukin.androidprofessionallevel.domain.useCase.GetShopItemUseCase

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        // TODO(): take oldData and replace
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name, count, true)
            editShopItemUseCase.editShopItem(shopItem)
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: java.lang.Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        if (name.isBlank()) {
            // TODO(): show error input name
            return false
        }

        if (count < 0) {
            // TODO(): show error input count
            return false
        }
        return true
    }
}