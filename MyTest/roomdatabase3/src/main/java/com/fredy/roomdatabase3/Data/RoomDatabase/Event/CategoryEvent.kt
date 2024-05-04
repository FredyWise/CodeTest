package com.fredy.roomdatabase3.Data.RoomDatabase.Event

import com.fredy.roomdatabase3.Data.RoomDatabase.Entity.Category
import com.fredy.roomdatabase3.Data.RoomDatabase.Enum.CategoryType
import com.fredy.roomdatabase3.Data.RoomDatabase.Enum.SortType

sealed interface CategoryEvent {
    object SaveCategory: CategoryEvent
    data class CategoryName(val categoryName: String): CategoryEvent
    data class CategoryTypes(val categoryType: CategoryType): CategoryEvent
    data class CategoryIcon(
        val icon: Int,
        val iconDescription: String
    ): CategoryEvent
    data class ShowDialog(val category: Category): CategoryEvent
    object HideDialog: CategoryEvent
    data class SortCategory(val sortType: SortType): CategoryEvent
    data class DeleteCategory(val category: Category): CategoryEvent
    object Dummy: CategoryEvent

}
