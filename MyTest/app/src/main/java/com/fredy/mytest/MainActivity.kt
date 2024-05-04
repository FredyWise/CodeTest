package com.fredy.mytest

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import com.fredy.mytest.ui.theme.MyTestTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity: ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTestTheme {
                val viewModel: MainViewModel = viewModel()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val permissionState = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                        rememberPermissionState(
                            permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ).status.isGranted
                    } else {
                        true
                    }

                    val context = LocalContext.current

                    val permissionLauncher = rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestPermission()
                    ) { isGranted: Boolean ->
                        if (isGranted) {
                            Toast.makeText(
                                context,
                                "Permission Granted",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Permission Denied",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    var showDirPicker  by remember { mutableStateOf(false) }
                     DirectoryPicker(
                        show = showDirPicker,
                        onFileSelected = {
                            viewModel.onFilePathsListChange(
                                listOf(it!!.toUri()),context
                            )
                        }
                    )
                    val state = viewModel.state

                    SideEffect {
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                            permissionLauncher.launch(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                15.dp
                            ),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(
                                    0.76f
                                )
                        ) {
                            if (state.filePaths.isEmpty()) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Pick some files")
                                }
                            }
                            if (state.filePaths.isNotEmpty()) {
                                LazyColumn {
                                    items(state.filePaths) { path ->
                                        Text(text = path)
                                        Spacer(
                                            modifier = Modifier.height(
                                                9.dp
                                            )
                                        )

                                    }
                                }
                            }
                        }
                        Button(onClick = {
                            if (permissionState) {

                            } else {
                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                                    permissionLauncher.launch(
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    )
                                }
                            }
                        }) {
                            Text(text = "Pick files")
                        }

                    }
                }
            }
        }
    }
}