package com.fredy.mysavingsscreens.Data.Database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.fredy.mysavingsscreens.Data.Database.Entity.Category
import com.fredy.mysavingsscreens.Data.Database.Enum.CategoryType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Upsert
    suspend fun upsertCategory(category: Category)
    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM category " +
            "WHERE category_id=:categoryId")
    fun getCategory(categoryId:Int): Flow<Category>

    @Query("SELECT * FROM category " +
            "WHERE userIdFk=:userId " +
            "ORDER BY categoryName ASC")
    fun getUserCategoriesOrderedByName(userId:Int): Flow<List<Category>>
    @Query("SELECT * FROM category " +
            "WHERE categoryType =:type " +
            "AND userIdFk=:userId " +
            "ORDER BY categoryName ASC")
    fun getExpenseCategoriesOrderedByName(type:CategoryType = CategoryType.Expense, userId:Int): Flow<List<Category>>
    @Query("SELECT * FROM category " +
            "WHERE categoryType = 'Income' " +
            "AND userIdFk=:userId " +
            "ORDER BY categoryName ASC")
    fun getIncomeCategoriesOrderedByName(userId:Int): Flow<List<Category>>
}

//remember to change the query bro