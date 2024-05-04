package com.fredy.roomdatabase3.Data.RoomDatabase.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.fredy.roomdatabase3.Data.RoomDatabase.Entity.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Upsert
    suspend fun upsertContact(account: Account)
    @Delete
    suspend fun deleteContact(account: Account)
    @Query("SELECT * FROM account " +
            "WHERE accountId=:accountId")
    fun getAccount(accountId:Int): Flow<Account>
    @Query("SELECT * FROM account " +
            "ORDER BY accountName ASC")
    fun getUserAccountOrderedByName(): Flow<List<Account>>
}