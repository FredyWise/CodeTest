package com.fredy.roomdatabase3.ui.Category

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fredy.roomdatabase3.Data.RoomDatabase.Entity.Category
import com.fredy.roomdatabase3.Data.RoomDatabase.Event.CategoryEvent
import com.fredy.roomdatabase3.ViewModel.CategoryViewModel
import com.fredy.roomdatabase3.ui.SimpleButton

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    Column(modifier = modifier) {
        if (state.isAddingCategory) {
            CategoryAddDialog(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        SimpleButton(
            modifier = Modifier
                .padding(
                    horizontal = 50.dp
                )
                .clip(
                    MaterialTheme.shapes.medium
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.medium
                ),
            onClick = {
                viewModel.onEvent(
                    CategoryEvent.ShowDialog(
                        Category()
                    )
                )
            },
            title = "ADD NEW CATEGORY",
        )
        CategoryBody(
            categoryMaps = state.categories,
            onEvent = viewModel::onEvent
        )
    }
}