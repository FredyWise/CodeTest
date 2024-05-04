package com.fredy.mysavingsscreens.Data.Database.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    var userName:String,
//    var password:String,
    var userCurrency: String = "USD",
    @ColumnInfo("user_id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
