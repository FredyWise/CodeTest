package com.fredy.roomdatabase3.ui.Repository

import com.fredy.roomdatabase3.Data.RoomDatabase.Dao.AccountDao
import com.fredy.roomdatabase3.Data.RoomDatabase.Entity.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun upsertAccount(account: Account)
    suspend fun deleteAccount(account: Account)
    fun getAccount(accountId: Int): Flow<Account>
    fun getUserAccountOrderedByName(): Flow<List<Account>>
}


class AccountRepositoryImpl(private val accountDao: AccountDao) : AccountRepository {
    override suspend fun upsertAccount(account: Account) {
        accountDao.upsertContact(account)
    }

    override suspend fun deleteAccount(account: Account) {
        accountDao.deleteContact(account)
    }

    override fun getAccount(accountId: Int): Flow<Account> {
        return accountDao.getAccount(accountId)
    }

    override fun getUserAccountOrderedByName(): Flow<List<Account>> {
        return accountDao.getUserAccountOrderedByName()
    }
}