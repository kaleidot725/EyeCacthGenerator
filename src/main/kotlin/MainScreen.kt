import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import configuration.ImageConfiguration
import org.jetbrains.jewel.foundation.theme.JewelTheme
import preview.ImagePreview

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var title by remember { mutableStateOf("") }
    var subTitle by remember { mutableStateOf("") }
    var width by remember { mutableIntStateOf(1920) }
    var height by remember { mutableIntStateOf(1080) }
    var startColor by remember { mutableStateOf(Color.Red) }
    var endColor by remember { mutableStateOf(Color.Blue) }

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
            title = title,
            onChangedTitle = { title = it },
            subTitle = subTitle,
            onChangedSubTitle = { subTitle = it},
            width = width,
            onChangedWidth = { width = it },
            height = height,
            onChangedHeight = { height = it },
            startColor = startColor,
            onChangedStartColor = { startColor = it },
            endColor = endColor,
            onChangedEndColor = { endColor = it },
            modifier = Modifier.fillMaxHeight().width(400.dp),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MainScreen()
}