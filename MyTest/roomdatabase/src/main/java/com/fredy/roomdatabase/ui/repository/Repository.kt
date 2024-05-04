package com.fredy.roomdatabase.ui.repository

import com.fredy.roomdatabase.Data.Room.ItemDao
import com.fredy.roomdatabase.Data.Room.ListDao
import com.fredy.roomdatabase.Data.Room.Models.Item
import com.fredy.roomdatabase.Data.Room.Models.ShoppingList
import com.fredy.roomdatabase.Data.Room.Models.Store
import com.fredy.roomdatabase.Data.Room.StoreDao

class Repository(
    private val listDao: ListDao,
    private val storeDao: StoreDao,
    private val itemDao: ItemDao,
) {
    val store = storeDao.getAllStores()
    val getItemsWithListAndStore = listDao.getItemsWithStoreAndList()

    fun getItemWithStoreAndList(id: Int) = listDao
        .getItemWithStoreAndListFilteredById(id)

    fun getItemWithStoreAndListFilteredById(id: Int) =
        listDao.getItemsWithStoreAndListFilteredById(id)

    suspend fun insertList(shoppingList: ShoppingList) {
        listDao.insertShoppingList(shoppingList)
    }

    suspend fun insertStore(store: Store) {
        storeDao.insert(store)
    }

    suspend fun insertItem(item: Item) {
        itemDao.insert(item)
    }

    suspend fun deleteItem(item: Item) {
        itemDao.delete(item)
    }

    suspend fun updateItem(item: Item) {
        itemDao.update(item)
    }

}