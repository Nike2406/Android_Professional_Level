package com.bukin.androidprofessionallevel.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bukin.androidprofessionallevel.data.ShopListRepositoryImpl
import com.bukin.androidprofessionallevel.domain.ShopItem
import com.bukin.androidprofessionallevel.domain.useCase.AddShopItemUseCase
import com.bukin.androidprofessionallevel.domain.useCase.EditShopItemUseCase
import com.bukin.androidprofessionallevel.domain.useCase.GetShopItemUseCase
import kotlinx.coroutines.launch

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    // LiveData
    /*
    * View должна только подписываться на объекты LD и реагировать на
    * изменения в них, но не должна сама вставлять значения
    * Если изменить на LiveData (т.е. без сеттера), то проблема исчезнет,
    * но есть более популярная реализация от Google с двумя переменными
    * (private & public)
    * */
    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName // рекомендация Google

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    /*
    * Т.к. нам нужно опопвестить экран, что его можно закрыть,
    * а тип передаваемого значения нам не важен, то исп. Unit
    * */
    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItem(shopItemId)
            _shopItem.postValue(item)
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            viewModelScope.launch {
                val shopItem = ShopItem(name, count, true)
                addShopItemUseCase.addShopItem(shopItem)
                finishWork()
            }
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name, count = count)
                    editShopItemUseCase.editShopItem(item)
                    finishWork()
                }
            }
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

    /*
    * Согласно MVVM нельзя создать ссылку на activity из ViewModel.
    * Вместо этого можно создать объект LiveData в VM, и кодга нужно будет отобразить
    * ошибку, мы отправим в LD значение, Activity подпишется на нее и в нужный
    * момент отобразит ошибку.
    * */
    private fun validateInput(name: String, count: Int): Boolean {
        if (name.isBlank()) {
            _errorInputName.value = true
            return false
        }

        if (count <= 0) {
            _errorInputCount.value = true
            return false
        }
        return true
    }

    /*
    * При повторном вводе пользователя, ошибку стоит убрать
    * */
    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.postValue(Unit)
    }
}