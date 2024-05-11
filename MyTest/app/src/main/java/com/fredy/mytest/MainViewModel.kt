package com.fredy.mytest

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredy.mysavings.Feature.Data.APIs.CurrencyModels.TabScannerAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val tabScannerAPI: TabScannerAPI,
): ViewModel() {

    var state by mutableStateOf(MainScreenState())
        private set

    private val uriPathFinder = UriPathFinder()

//    fun onFilePathsListChange(
//        list: List<Uri>, context: Context
//    ) {
//        val updatedList = state.filePaths.toMutableList()
//        val paths = changeUriToPath(list, context)
//        viewModelScope.launch {
//            updatedList += paths
//            state = state.copy(
//                filePaths = updatedList
//            )
//        }
//    }
//
//    private fun changeUriToPath(
//        uris: List<Uri>, context: Context
//    ): List<String> {
//        val pathsList: MutableList<String> = mutableListOf()
//        uris.forEach {
//            val path = uriPathFinder.getPath(
//                context, it
//            )
//            pathsList.add(path!!)
//        }
//        return pathsList
//    }

    fun createImageUri(context: Context): Uri {
        val timeStamp = SimpleDateFormat(
            "yyyy_MM_dd_HH_mm_ss",
            Locale.getDefault()
        ).format(
            Date()
        )
        val imageFileName = "JPEG_${timeStamp}_"
        val imageFile = createImageFile(
            context, imageFileName
        )

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            imageFile
        )
    }

    fun createImageFile(
        context: Context,
        imageFileName: String,
        environment: String? = null
    ): File {
        val storageDir = context.getExternalFilesDir(
            environment
        )
        return File.createTempFile(
            imageFileName, ".jpg", storageDir
        )
    }

    fun processImage(
        imageUri: Uri, context: Context
    ) {
        viewModelScope.launch {
            Log.e(
                "TAG",
                "processImage: start",
            )
            withContext(Dispatchers.IO) {
                val imagePart = createImagePart(
                    context, imageUri
                )
                Log.e(
                    "TAG",
                    "processImage: start2",
                )
                val processResponse = tabScannerAPI.processReceipt(
                    imagePart
                ).execute()
                if (processResponse.isSuccessful) {
                    Log.e(
                        "TAG",
                        "processImage: start3",
                    )
                    val token = processResponse.body()?.token ?: return@withContext
                    Log.e(
                        "TAG",
                        "processImage: $token",
                    )
                    delay(5000)
                    getResult(token)
                } else {
                    Log.e(
                        "TAG",
                        "processImage: ",
                    )
                }
            }

        }
        Log.e(
            "TAG",
            "processImage: finish",
        )
    }


    // Function to retrieve the result using the token
    private fun getResult(token: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val resultResponse = tabScannerAPI.getResult(
                    token
                ).execute()
                if (resultResponse.isSuccessful) {
                    val resultData = resultResponse.body()
                    Log.e("TAG", "getResult: $resultData", )
                    // Handle the result data
                } else {
                    // Handle API call failure
                }
            }
        }
    }

    suspend fun createImagePart(
        context: Context, imageUri: Uri
    ): MultipartBody.Part {
        return withContext(Dispatchers.IO) {
            val parcelFileDescriptor = context.contentResolver.openFileDescriptor(
                imageUri, "r"
            )
            val file = File(
                context.cacheDir,
                context.contentResolver.getFileName(
                    imageUri
                )
            )
            parcelFileDescriptor?.let {
                val inputStream = FileInputStream(
                    it.fileDescriptor
                )
                val outputStream = FileOutputStream(
                    file
                )
                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream.write(
                        buffer, 0, length
                    )
                }
                inputStream.close()
                outputStream.close()
            }


            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

            MultipartBody.Part.createFormData(
                "file", file.name, requestFile
            )
        }
    }
}

fun ContentResolver.getFileName(uri: Uri): String {
    var name = ""
    val returnCursor = this.query(
        uri, null, null, null, null
    )
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(
            OpenableColumns.DISPLAY_NAME
        )
        returnCursor.moveToFirst()
        name = returnCursor.getString(
            nameIndex
        )
        returnCursor.close()
    }
    return name
}

