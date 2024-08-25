package view.component.image

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.component.CircularProgressIndicator
import java.io.File
import java.util.Date

@Composable
fun ImagePreview(
    file: File,
    date: Long,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    val result by remember(date) {
        mutableStateOf(
            try {
                val bitmap = loadImageBitmap(file.inputStream())
                Result.success(bitmap)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }
    Box(modifier) {
        result.getOrNull()?.let {
            Image(
                bitmap = it,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(100.dp).align(Alignment.Center)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.size(500.dp)) {
        ImagePreview(File(""), Date().time, false)
    }
}