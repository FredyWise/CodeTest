package com.fredy.roomdatabase3

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.screens.authentication.SignIn
import com.fredy.roomdatabase3.Data.RoomDatabase.Event.AccountEvent
import com.fredy.roomdatabase3.Data.RoomDatabase.Event.CategoryEvent
import com.fredy.roomdatabase3.Data.RoomDatabase.Event.RecordsEvent
import com.fredy.roomdatabase3.ViewModel.AccountViewModel
import com.fredy.roomdatabase3.ViewModel.CategoryViewModel
import com.fredy.roomdatabase3.ViewModel.RecordViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestingScaffold(
    AviewModel: AccountViewModel = viewModel(),
    CviewModel: CategoryViewModel = viewModel(),
    RviewModel: RecordViewModel = viewModel()
) {
//    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                AviewModel.onEvent(AccountEvent.Dummy)
                CviewModel.onEvent(CategoryEvent.Dummy)
                RviewModel.onEvent(RecordsEvent.Dummy)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add contact"
                )
            }
        },
    ) { innerPadding ->
//        AccountsScreen(modifier = Modifier.padding(innerPadding),viewModel = AviewModel)
//        CategoriesScreen(modifier = Modifier.padding(innerPadding),viewModel = CviewModel)
//        RecordsScreen(modifier = Modifier.padding(innerPadding),viewModel = RviewModel)
        SignIn(
            gotoHome = { /*TODO*/ },
            gotoSignUp = { /*TODO*/ }) {
            
        }
    }
}