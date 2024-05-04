package com.fredy.train2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MyApp() }
    }
}

@Composable
fun MyCounter(
    modifier: Modifier = Modifier,
    count: Int,
    showTask: Boolean,
    onIncrement: () -> Unit,
    onReset: () -> Unit,
    onClose: () -> Unit,
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            if (showTask) {
                MyTaskItem(
                    taskName = "Have you taken your 15 minute walk today?",
                    onClose = onClose,
                )
            }
            Text(
                text = "You've had $count glasses."
            )
        }
        Row {
            Button(
                onClick = onIncrement,
                enabled = count < 10,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = "Add One")
            }
            Button(
                onClick = onReset,
                enabled = count > 0,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = "Reset")
            }
        }
    }
}

class MyTask(
    val id: Int,
    val label: String,
    initialChecked: Boolean = false
) {
    var checked by mutableStateOf(initialChecked)
}

@Composable
fun MyTaskItem(
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = taskName,
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = 16.dp
                )
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close"
            )
        }
    }
}

@Composable
fun MyTaskItem(
    taskName: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var checkedState by rememberSaveable {
        mutableStateOf(
            false
        )
    }

    MyTaskItem(
        taskName = taskName,
        checked = checkedState,
        onCheckedChange = { checkedState = !checkedState },
        onClose = onClose,
        modifier = modifier,
    )
}

@Composable
fun MyTaskList(
    modifier: Modifier = Modifier,
    onClose: (MyTask) -> Unit,
    onCheckedChange: (MyTask, Boolean) -> Unit,
    list: List<MyTask>
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = list,
            key = { task -> task.id }) { task ->
            MyTaskItem(
                taskName = task.label,
                onClose = { onClose(task) },
                checked = task.checked,
                onCheckedChange = { checked ->
                    onCheckedChange(
                        task, checked
                    )
                },
            )
        }
    }

}


class MyViewModel: ViewModel() {
    private val _tasks = getMyTasks().toMutableStateList()
    val tasks: List<MyTask>
        get() = _tasks

    fun remove(item: MyTask) {
        _tasks.remove(item)
    }

    fun changeTaskChecked(
        item: MyTask, checked: Boolean
    ) = tasks.find { it.id == item.id }?.let { task ->
        task.checked = checked
    }
}

@Composable
fun MyScreen(
    modifier: Modifier = Modifier,
    myViewModel: MyViewModel = viewModel()
) {
    var count: Int by rememberSaveable {
        mutableStateOf(0)
    }
    var showTask: Boolean by rememberSaveable {
        mutableStateOf(true)
    }
    Column(
        modifier = modifier
    ) {
        MyCounter(count = count,
            showTask = showTask,
            onClose = { showTask = false },
            onReset = {
                showTask = true
                count = 0
            },
            onIncrement = { count++ })
        MyTaskList(onClose = { task ->
            myViewModel.remove(
                task
            )
        },
            onCheckedChange = { task, checked ->
                myViewModel.changeTaskChecked(
                    task, checked
                )
            },
            list = myViewModel.tasks
        )
    }

}


private fun getMyTasks() = List(30) { i ->
    MyTask(
        i, label = "Task # $i"
    )
}

@Composable
fun MyApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MyScreen()
    }
}