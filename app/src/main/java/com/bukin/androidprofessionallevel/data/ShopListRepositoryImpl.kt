package com.bukin.androidprofessionallevel.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bukin.androidprofessionallevel.data.mapper.ShopListMapper
import com.bukin.androidprofessionallevel.domain.ShopItem
import com.bukin.androidprofessionallevel.domain.repository.ShopListRepository

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {

    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

//    /**
//     * MediatorLiveData - перехватчик из одной liveData в другую с возможностью
//     * объявления условий, либо особого способа реагирования
//     * Через apply{} установить:
//     * 1. Объекты какой liveData нужно перехватывать через addSource(источник)
//     * 2. Лямбда-варажение, которое будет вызвано при каждом изменении в
//     * оригинальной ld
//     */
//    override fun getShopList(): LiveData<List<ShopItem>> =
//        MediatorLiveData<List<ShopItem>>().apply {
//            // Добавляем исходный ресурс, который будем перехватывать
//            addSource(shopListDao.getShopList()) {
//                // Здесь пишем все, что нам нужно сделать с коллекцией
//                value = mapper.mapListDbModelToListEntity(it)
//            }
//        }

    /**
    * Если нужно просто преобразовать одну liveData в другую, то можно
    * использовать метод map из класса Transformations
    * */
    override fun getShopList(): LiveData<List<ShopItem>> =
        Transformations.map(
            // Что преоразовываем
            shopListDao.getShopList()
        ) {
            // Как преоразовываем
            mapper.mapListDbModelToListEntity(it)
        }
}