package com.bukin.androidprofessionallevel.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bukin.androidprofessionallevel.data.ShopListRepositoryImpl
import com.bukin.androidprofessionallevel.domain.ShopItem
import com.bukin.androidprofessionallevel.domain.useCase.DeleteShopItemUseCase
import com.bukin.androidprofessionallevel.domain.useCase.EditShopItemUseCase
import com.bukin.androidprofessionallevel.domain.useCase.GetShopListUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // DI in future
    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

//    /**
//     * Dispatcher - определяет поток, на котором будет выполняться корутина
//     * Main - главный поток.
//     * Default - фоновый поток, использует пул потоков фиксированной длинны,
//     * будут использованы все ядра процессора(макс число потоков - число ядер
//     * в процессоре), исп-ся для сложных вычислений.
//     * IO - фоновый поток, исп-ся для оппераций чтения и записи. Исп-т
//     * cashed thread pool, т.е. если свободных поток нет, а прилетела новая
//     * задача, он создаст новый поток (до 64-х потоков).
//     * */
//    private val scope = CoroutineScope(Dispatchers.Main)

    /*
    * Взаимодействие Activity и ViewModel должно происходить через LiveData()
    * для сохранения данных при уничтожении activity
    * */
    // Наследник LiveData
    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        /**
        * viewModelScope - автоматически будет работать в главном потоке
         * и очищен в методе onCleared()
        * */
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newShopItem)
        }
    }
}