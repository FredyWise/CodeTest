package com.fredy.roomdatabase3.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredy.roomdatabase3.Data.RoomDatabase.Entity.Category
import com.fredy.roomdatabase3.Data.RoomDatabase.Enum.CategoryType
import com.fredy.roomdatabase3.Data.RoomDatabase.Enum.SortType
import com.fredy.roomdatabase3.Data.RoomDatabase.Event.CategoryEvent
import com.fredy.roomdatabase3.R
import com.fredy.roomdatabase3.ui.Repository.CategoryRepositoryImpl
import com.fredy.roomdatabase3.ui.Repository.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepository: CategoryRepositoryImpl = Graph.categoryRepository,
): ViewModel() {
    private val _sortType = MutableStateFlow(
        SortType.ASCENDING
    )

    private val _categories = categoryRepository.getUserCategoriesOrderedByName().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    private val _state = MutableStateFlow(
        CategoryState()
    )

    val state = combine(
        _state, _sortType, _categories,
    ) { state, sortType, categories ->
        state.copy(categories = categories.groupBy {
            it.categoryType
        }.toSortedMap().map {
            CategoryMap(
                categoryType = it.key,
                categories = it.value
            )
        }, sortType = sortType
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CategoryState()
    )


    fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        categoryId = event.category.categoryId,
                        categoryName = event.category.categoryName,
                        categoryType = event.category.categoryType,
                        categoryIconDescription = event.category.categoryIconDescription,
                        categoryIcon = event.category.categoryIcon,
                        isAddingCategory = true
                    )
                }
            }

            is CategoryEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingCategory = false
                    )
                }
            }

            is CategoryEvent.DeleteCategory -> {
                viewModelScope.launch {
                    categoryRepository.deleteCategory(
                        event.category
                    )
                }
            }

            is CategoryEvent.SaveCategory -> {
                val categoryId = state.value.categoryId
                val categoryName = state.value.categoryName
                val categoryType = state.value.categoryType
                val categoryIcon = state.value.categoryIcon
                val categoryIconDescription = state.value.categoryIconDescription

                if (categoryName.isBlank() || categoryIcon == 0 || categoryIconDescription.isBlank()) {
                    return
                }

                val category = Category(
                    categoryId = categoryId!!,
                    categoryName = categoryName,
                    categoryType = categoryType,
                    categoryIconDescription = categoryIconDescription,
                    categoryIcon = categoryIcon,
                )
                viewModelScope.launch {
                    categoryRepository.upsertCategory(
                        category
                    )
                }
                _state.update {
                    it.copy(
                        categoryId = null,
                        categoryName = "",
                        categoryIcon = 0,
                        categoryIconDescription = "",
                        isAddingCategory = false,
                    )
                }
            }

            is CategoryEvent.CategoryName -> {
                _state.update {
                    it.copy(
                        categoryName = event.categoryName
                    )
                }
            }

            is CategoryEvent.CategoryTypes -> {
                _state.update {
                    it.copy(
                        categoryType = event.categoryType
                    )
                }
            }

            is CategoryEvent.CategoryIcon -> {
                _state.update {
                    it.copy(
                        categoryIcon = event.icon,
                        categoryIconDescription = event.iconDescription
                    )
                }
            }


            is CategoryEvent.SortCategory -> {
                _sortType.value = event.sortType
            }

            is CategoryEvent.Dummy ->{
                val account = Category(
                    categoryName = "babi",
                    categoryType = CategoryType.Expense,
                    categoryIcon = R.drawable.ic_launcher_background,
                    categoryIconDescription = "bodoh",
                )
                viewModelScope.launch {
                    Log.e(
                        "BABI",
                        "onEvent: " + state.value + _categories.value
                    )
                    categoryRepository.upsertCategory(
                        account
                    )
                }
            }
        }
    }
}

data class CategoryState(
    val categories: List<CategoryMap> = listOf(),
    val categoryId: Int? = null,
    val categoryName: String = "",
    val categoryType: CategoryType = CategoryType.Expense,
    val categoryIcon: Int = 0,
    val categoryIconDescription: String = "",
    val isAddingCategory: Boolean = false,
    val sortType: SortType = SortType.ASCENDING
)

data class CategoryMap(
    val categoryType: CategoryType,
    val categories: List<Category>
)