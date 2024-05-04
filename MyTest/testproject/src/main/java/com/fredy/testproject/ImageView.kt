package com.fredy.testproject

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter

@Composable
fun ImageView(photoUri: Uri) {
    Image(
        painter = rememberImagePainter(
            photoUri
        ),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}