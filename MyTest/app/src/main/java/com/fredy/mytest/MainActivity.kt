package com.fredy.mytest

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import com.fredy.mytest.ui.theme.MyTestTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTestTheme {

                CameraAPP()
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraAPP() {
    val context = LocalContext.current
    val viewModel: MainViewModel = hiltViewModel()
    val uri = viewModel.createImageUri(
        context
    )
    var capturedImageUri by remember {
        mutableStateOf<Uri>(
            Uri.EMPTY
        )
    }
    var detectedText: String by remember {
        mutableStateOf(
            ""
        )
    }
    val permissionsState = rememberPermissionState(
        Manifest.permission.CAMERA
    )
    val imageCropLauncher = rememberLauncherForActivityResult(
        CropImageContract()
    ) {result ->
        if (result.isSuccessful) {
            capturedImageUri = result.uriContent!!
            viewModel.processImage(capturedImageUri,context)
        }
        else {
            val exception = result.error
        }
    }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                val cropOption = CropImageContractOptions(it, CropImageOptions())
                imageCropLauncher.launch(cropOption)
            }
        },)
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            val cropOption = CropImageContractOptions(uri, CropImageOptions())
            imageCropLauncher.launch(cropOption)

        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(
                context,
                "Permission Granted",
                Toast.LENGTH_SHORT
            ).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(
                context,
                "Permission Denied",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (capturedImageUri != Uri.EMPTY) {
            Image(
                contentDescription = "Captured Image",
                painter = rememberImagePainter(
                    capturedImageUri
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(
                        1f
                    )
            )
        } else {
            Image(
                contentDescription = "Captured Image",
                imageVector = Icons.Default.Add,
                modifier = Modifier
                    .size(100.dp)
                    .weight(
                        1f
                    )
            )
        }
        Row {
            Button(
                onClick = {
                    if (permissionsState.status.isGranted) {
                        cameraLauncher.launch(
                            uri
                        )
                    } else {
                        permissionLauncher.launch(
                            Manifest.permission.CAMERA
                        )
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(
                        48.dp
                    )
            ) {
                Text(text = "Capture Image")
            }
            Button(
                onClick = {
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .height(
                        48.dp
                    )
            ) {
                Text(text = "Gallery")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        TextBox(
            value = detectedText,
            onValueChanged = { detectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .weight(
                    0.25f
                )
        )
    }
}