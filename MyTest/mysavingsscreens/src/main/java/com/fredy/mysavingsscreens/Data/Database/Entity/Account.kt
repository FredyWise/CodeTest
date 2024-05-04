package com.fredy.mysavingsscreens.Data.Database.Entity

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fredy.mysavings.Data.Balance

@Entity
data class Account(
    @ColumnInfo("account_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userIdFk: Int,
    var recordIdFk: Int,
    var accountName: String = "Account",
    var accountBalance: Balance = Balance(),
    var accountIcon: Int,
    var accountIconDescription: String = "",
    var accountIconColor: Color = Color.Unspecified,
)