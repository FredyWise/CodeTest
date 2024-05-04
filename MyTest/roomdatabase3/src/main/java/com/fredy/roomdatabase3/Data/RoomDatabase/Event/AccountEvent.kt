package com.fredy.roomdatabase3.Data.RoomDatabase.Event

import com.fredy.roomdatabase3.Data.RoomDatabase.Entity.Account
import com.fredy.roomdatabase3.Data.RoomDatabase.Enum.SortType

sealed interface AccountEvent{
    object SaveAccount: AccountEvent
    data class AccountName(val accountName: String) :AccountEvent
    data class AccountAmount(val amount: String): AccountEvent
    data class AccountCurrency(val currency: String): AccountEvent
    data class AccountIcon(
        val icon: Int,
        val iconDescription: String
    ): AccountEvent

    data class ShowDialog(val account: Account): AccountEvent
    object HideDialog: AccountEvent
//    object ShowOption: AccountEvent
//    object HideOption: AccountEvent
    data class SortAccount(val sortType: SortType): AccountEvent
    data class DeleteAccount(val account: Account): AccountEvent

    object Dummy: AccountEvent
}

//need change