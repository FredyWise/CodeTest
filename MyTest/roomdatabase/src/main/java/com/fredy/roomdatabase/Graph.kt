package com.fredy.roomdatabase

import android.content.Context
import com.fredy.roomdatabase.Data.Room.ShoppingListDatabase
import com.fredy.roomdatabase.ui.repository.Repository

object Graph {
    lateinit var db: ShoppingListDatabase
        private set

    val repository by lazy {
        Repository(
            listDao = db.listDao(),
            storeDao = db.storeDao(),
            itemDao = db.itemDao()
        )
    }

    fun provide(context: Context){
        db = ShoppingListDatabase.getDatabase(context)
    }
}