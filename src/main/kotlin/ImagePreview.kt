import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ImagePreview(
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Text(text = "PREVIEW SAMPLE", modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.size(500.dp)) {
        ImagePreview()
    }
}