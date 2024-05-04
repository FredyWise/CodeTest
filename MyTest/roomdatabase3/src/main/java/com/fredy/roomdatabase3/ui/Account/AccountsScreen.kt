package com.fredy.roomdatabase3.ui.Account

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fredy.roomdatabase3.Data.RoomDatabase.Entity.Account
import com.fredy.roomdatabase3.Data.RoomDatabase.Event.AccountEvent
import com.fredy.roomdatabase3.ViewModel.AccountViewModel
import com.fredy.roomdatabase3.ui.SimpleButton

@Composable
fun AccountsScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    Column(modifier = modifier) {
        if (state.isAddingAccount) {
            AccountAddDialog(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
        AccountHeader(
            modifier = Modifier
                .height(150.dp)
                .padding(
                    vertical = 4.dp
                )
                .background(
                    MaterialTheme.colorScheme.surface
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.large
                ), state = state
        )
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
                    AccountEvent.ShowDialog(
                        Account()
                    )
                )
            },
            title = "ADD NEW ACCOUNT",
        )
        AccountBody(
            accounts = state.accounts,
            onEvent = viewModel::onEvent
        )
    }
}

