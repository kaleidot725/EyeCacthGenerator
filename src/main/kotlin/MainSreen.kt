import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {
    Row {
        ImagePreview(
            modifier = Modifier.fillMaxHeight().weight(1.0f).background(Color.Red)
        )
        ImageConfiguration(
            modifier = Modifier.fillMaxHeight().width(400.dp).background(Color.Blue)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MainScreen()
}