import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CropFree
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Scanner
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

private const val REQUEST_IMAGE_CAPTURE = 1

@Composable
fun ScannerScreen() {
    val context = LocalContext.current
    val activity = LocalView.current.context as ComponentActivity

    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val permissionState = rememberMultiplePermissionsState(listOf(android.Manifest.permission.CAMERA))

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Capture Image Button
        Button(
            onClick = {
                if (permissionState.allPermissionsGranted) {
                    scope.launch {
                        val imageFile = createImageFile(context)
                        imageFile?.let { file ->
                            takePicture(activity, file)
                        }
                    }
                } else {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(android.Manifest.permission.CAMERA),
                        REQUEST_IMAGE_CAPTURE
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "Capture Image")
        }

        // Display Captured Image
        imageBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        // Detect Text Button
        Button(
            onClick = {
                imageBitmap?.let { bitmap ->
                    detectTextFromImage(context, bitmap)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colors.primary)
        ) {
            Text(text = "Detect Text", color = Color.White)
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Navigate to Scanner Screen
        Button(
            onClick = {
                navController.navigate("scanner")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "Go to Scanner Screen")
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen()
        }
        composable("scanner") {
            ScannerScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density

    val navController = rememberNavController()

    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val contentPadding = LocalWindowInsets.current.systemBars
        .toPaddingValues()
        .copy(bottom = with(LocalDensity.current) { 16.dp.toPx() })

    val multiplePermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.CAMERA
        )
    )

    val actions = remember {
        MyImeActions(
            onGoClick = {
                navController.navigate("scanner")
            },
            onHideKeyboardClick = {
                softwareKeyboardController?.hide()
            }
        )
    }

    val filenameState = remember { mutableStateOf("file.txt") }
    val fileContentState = remember { mutableStateOf("Hello, World!") }
    val imageUriState = remember { mutableStateOf("") }

    var selectedTabIndex by remember { mutableStateOf(0) }
    val getContent = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle the result here
            val data: Intent? = result.data
            // Process the data as needed
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Jetpack Compose MLKit Text Detection")
                },
                actions = {
                    if (selectedTabIndex == 1) {
                        MyIconToggleButton(
                            checked = false,
                            onCheckedChange = { /* TODO */ }
                        ) {
                            Icon(
                                imageVector = Icons.Default.CropFree,
                                contentDescription = null
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        MyIconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                                    addCategory(Intent.CATEGORY_OPENABLE)
                                    type = "*/*"
                                }

                            },
                            icon = Icons.Default.Attachment,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            )
        },
        content = { padding ->
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                when (currentRoute) {
                    "main" -> {
                        MainScreen()
                    }
                    "scanner" -> {
                        ScannerScreen()
                    }
                    else -> {
                        MainScreen()
                    }
                }
            }
        },
        bottomBar = {
            val tabs = listOf("Main", "Scanner")
            BottomNavigation(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White,
                contentColor = Color.Gray
            ) {
                tabs.forEachIndexed { index, title ->
                    BottomNavigationItem(icon = {
                        Icon(
                            imageVector = when (index) {
                                0 -> Icons.Default.Home
                                1 -> Icons.Default.Scanner
                                else -> Icons.Default.Home
                            },
                            contentDescription = null
                        )
                    },
                        label = { Text(text = title) },
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index // Update the selectedTabIndex when a tab is clicked
                            navController.navigate(
                                route = when (index) {
                                    0 -> "main"
                                    1 -> "scanner"
                                    else -> "main"
                                }
                            )
                        })
                }
            }
        }
    )
}

@Composable
fun MyIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // You can customize the appearance and behavior of your toggle button here
    IconButton(
        modifier = modifier,
        onClick = { onCheckedChange(!checked) },
    ) {
        Icon(
            imageVector = if (checked) Icons.Default.Check else Icons.Default.Close,
            contentDescription = null
        )
    }
}

@Composable
fun MyIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    // You can customize the appearance and behavior of your icon button here
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }
}
private fun createImageFile(context: Context): File? {
    // Generate a unique file name or use a timestamp
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File? = context.getExternalFilesDir(
        Environment.DIRECTORY_PICTURES)
    return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
}