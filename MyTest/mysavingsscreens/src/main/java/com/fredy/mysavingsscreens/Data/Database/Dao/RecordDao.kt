package com.fredy.mysavingsscreens.Data.Database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.fredy.mysavingsscreens.Data.Database.Entity.Record
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Upsert
    suspend fun upsertRecordItem(recordItem: Record)
    @Delete
    suspend fun deleteRecordItem(recordItem: Record)
    @Query("SELECT * FROM record " +
            "WHERE userIdFk=:userId " +
            "ORDER BY recordDateTime ASC")
    fun getUserRecordsOrderedByDateTime(userId:Int): Flow<List<Record>>

    @Query("SELECT r.* FROM record AS r " +
            "INNER JOIN category AS c ON r.categoryIdFk = c.category_id " +
            "WHERE c.category_id=:categoryId " +
            "AND userIdFk=:userId " +
            "ORDER BY r.recordDateTime ASC")
    fun getUserCategoryRecordsOrderedByDateTime(categoryId: Int, userId:Int): Flow<List<Record>>

    @Query("SELECT r.* FROM record AS r " +
            "INNER JOIN account AS a1 ON r.accountIdFromFk = a1.account_id " +
            "INNER JOIN account AS a2 ON r.accountIdToFk = a2.account_id " +
            "WHERE (a1.account_id=:accountId " +
            "OR a2.account_id=:accountId) " +
            "AND userIdFk=:userId " +
            "ORDER BY r.recordDateTime ASC")
    fun getUserAccountRecordsOrderedByDateTime(accountId: String, userId:Int): Flow<List<Record>>

    @Query("SELECT SUM(recordBalance) FROM record " +
            "WHERE userIdFk=:userId")
    fun getUserTotalBalance(userId:Int): Flow<Double>

    @Query("SELECT SUM(recordBalance) FROM record " +
            "WHERE recordBalance < 0 " +
            "AND userIdFk=:userId")
    fun getUserTotalExpenses(userId:Int): Flow<Double>

    @Query("SELECT SUM(recordBalance) FROM record " +
            "WHERE recordBalance >= 0 " +
            "AND userIdFk=:userId")
    fun getUserTotalIncomes(userId:Int): Flow<Double>
}