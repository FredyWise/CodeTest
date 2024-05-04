package com.fredy.roomdatabase3.Data.RoomDatabase.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fredy.roomdatabase3.R

@Entity
data class Account(
    @PrimaryKey(autoGenerate = true)
    val accountId: Int = 0,
    val accountName: String = "",
    var accountAmount: Double = 0.0,
    var accountCurrency: String = "",
    val accountIcon: Int = 0,
    val accountIconDescription: String = "",
)

