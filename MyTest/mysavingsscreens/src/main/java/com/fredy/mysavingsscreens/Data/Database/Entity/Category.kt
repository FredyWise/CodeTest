package com.fredy.mysavingsscreens.Data.Database.Entity

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fredy.mysavingsscreens.Data.Database.Enum.CategoryType
@Entity
data class Category(
    @ColumnInfo("category_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userIdFk: Int,
    var categoryName: String,
    val categoryType: CategoryType = CategoryType.Expense,
    var categoryIcon: Int,
    var categoryIonDescription: String = "",
    var categoryIconColor: Color = Color.Unspecified,
)

