package com.fredy.mysavings.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fredy.mysavings.Data.FormatBalanceAmount
import com.fredy.mysavingsscreens.Data.Database.Entity.User
import com.fredy.mysavings.ui.component.Account.AccountHeader
import com.fredy.mysavings.ui.component.BasicButton
import com.fredy.mysavingsscreens.R

@Composable
fun AccountsScreen(
    modifier: Modifier = Modifier,

    ) {
    LazyColumn() {
        item {
            AccountHeader()
        }
        items(User.accounts) { account ->
            BasicButton(modifier = Modifier.padding(
                horizontal = 10.dp
            ),
                buttonBackgroundColor = MaterialTheme.colorScheme.surface,
                onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(
                        account.icon
                    ),
                    contentDescription = account.iconDescription,
                    tint = account.iconColor,
                    modifier = Modifier.width(
                        width = 60.dp,
                    )
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,

                    ) {
                    Text(
                        text = account.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(id = R.string.balance_capitalized) + ": " + FormatBalanceAmount(
                            balance = account.balance
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}