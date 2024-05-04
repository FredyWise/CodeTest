package com.fredy.roomdatabase.Data.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fredy.roomdatabase.Data.Room.Converter.DateConverter
import com.fredy.roomdatabase.Data.Room.Models.Item
import com.fredy.roomdatabase.Data.Room.Models.ShoppingList
import com.fredy.roomdatabase.Data.Room.Models.Store

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [ShoppingList::class, Item::class, Store::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingListDatabase: RoomDatabase() {
    abstract fun listDao():ListDao
    abstract fun itemDao():ItemDao
    abstract fun storeDao():StoreDao

    companion object{
        @Volatile
        var INSTANCE:ShoppingListDatabase? = null
        fun getDatabase(context: Context):ShoppingListDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    ShoppingListDatabase::class.java,
                    "shopping_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}