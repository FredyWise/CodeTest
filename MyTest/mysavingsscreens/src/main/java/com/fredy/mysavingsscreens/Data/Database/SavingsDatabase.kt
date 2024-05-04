package com.fredy.mysavingsscreens.Data.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fredy.mysavingsscreens.Data.Database.Converter.DateTimeConverter
import com.fredy.mysavingsscreens.Data.Database.Dao.AccountDao
import com.fredy.mysavingsscreens.Data.Database.Dao.CategoryDao
import com.fredy.mysavingsscreens.Data.Database.Dao.RecordDao
import com.fredy.mysavingsscreens.Data.Database.Dao.UserDao
import com.fredy.mysavingsscreens.Data.Database.Entity.Account
import com.fredy.mysavingsscreens.Data.Database.Entity.Category
import com.fredy.mysavingsscreens.Data.Database.Entity.Record
import com.fredy.mysavingsscreens.Data.Database.Entity.User
@TypeConverters(value = [DateTimeConverter::class])
@Database(
    entities = [User::class,Record::class, Account::class, Category::class],
    version = 1
)
abstract class SavingsDatabase: RoomDatabase() {
    abstract val userDao: UserDao
    abstract val recordDao: RecordDao
    abstract val accountDao: AccountDao
    abstract val categoryDao: CategoryDao


}