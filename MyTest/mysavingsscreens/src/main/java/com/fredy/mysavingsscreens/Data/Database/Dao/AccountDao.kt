package com.fredy.mysavingsscreens.Data.Database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.fredy.mysavingsscreens.Data.Database.Entity.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Upsert
    suspend fun upsertContact(account: Account)
    @Delete
    suspend fun deleteContact(account: Account)
    @Query("SELECT * FROM account " +
            "WHERE account_id=:accountId")
    fun getAccount(accountId:Int): Flow<Account>
    @Query("SELECT * FROM account " +
            "WHERE userIdFk=:userId " +
            "ORDER BY accountName ASC")
    fun getUserContactsOrderedByName(userId:Int): Flow<List<Account>>
}