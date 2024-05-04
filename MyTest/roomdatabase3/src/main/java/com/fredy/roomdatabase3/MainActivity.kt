package com.fredy.roomdatabase3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fredy.roomdatabase3.ui.Repository.Graph
import com.fredy.roomdatabase3.ui.theme.MyTestTheme

class MainActivity: ComponentActivity() {
//    private val db by lazy {
//        Room.databaseBuilder(
//            applicationContext,
//            SavingsDatabase::class.java,
//            "savings_database"
//        ).build()
//    }
//    private val viewModel by viewModels<RecordViewModel>(
//        factoryProducer = {
//            object : ViewModelProvider.Factory {
//                override fun <T: ViewModel> create(
//                    modelClass: Class<T>
//                ): T {
//                    return RecordViewModel(Graph.recordRepository) as T
//                }
//            }
//        },
//
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Graph.provideAppContext(this)
        setContent {
            MyTestTheme {
                // A surface container using the 'background' color from the theme
                TestingScaffold()
            }
        }
    }
}

