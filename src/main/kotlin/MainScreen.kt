import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import configuration.ImageConfiguration
import org.jetbrains.jewel.foundation.theme.JewelTheme
import preview.ImagePreview

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
    ) {
        ImagePreview(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1.0f)
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(JewelTheme.globalColors.borders.normal)
        )
        ImageConfiguration(
            modifier = Modifier
                .fillMaxHeight()
                .width(400.dp)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MainScreen()
}