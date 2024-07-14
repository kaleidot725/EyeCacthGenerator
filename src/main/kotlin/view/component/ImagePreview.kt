package view.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import java.io.File
import androidx.compose.ui.res.loadImageBitmap

@Composable
fun ImagePreview(
    file: File,
    modifier: Modifier = Modifier,
) {
    val result by remember(file) {
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
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.size(500.dp)) {
        ImagePreview(File(""))
    }
}