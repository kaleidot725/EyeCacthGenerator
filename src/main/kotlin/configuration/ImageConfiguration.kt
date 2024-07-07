package configuration

import component.TitleText
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField

@Composable
fun ImageConfiguration(
    modifier: Modifier = Modifier,
) {
    val controller = rememberColorPickerController()

    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TitleText("Text")

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Title",
                modifier = Modifier.width(125.dp).align(Alignment.CenterVertically)
            )

            TextField(
                value = "Hello World!!",
                onValueChange = {},
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "SubTitle",
                modifier = Modifier.width(125.dp).align(Alignment.CenterVertically)
            )

            TextField(
                value = "Hello World!!",
                onValueChange = {},
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            )
        }

        TitleText("Size")

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Width",
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            )

            TextField(
                value = "1920",
                onValueChange = {},
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            )

            Text(
                text = "Height",
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            )

            TextField(
                value = "1080",
                onValueChange = {},
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            )
        }

        TitleText("Color")

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Start",
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            )

            TextField(
                value = "#00ff00",
                onValueChange = {},
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            )

            Text(
                text = "End",
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            )

            TextField(
                value = "#00ff00",
                onValueChange = {},
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            )
        }

        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(10.dp),
            controller = controller,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                // do something
            },
            initialColor = Color.Red
        )

        AlphaSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp),
            controller = controller,
        )

        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp)
                .align(Alignment.CenterHorizontally),
            controller = controller,
        )

        Spacer(modifier = Modifier.weight(1.0f))

        Row(
            modifier = Modifier.align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DefaultButton(
                onClick = {}
            ) {
                Text("Save")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.size(500.dp)) {
        ImageConfiguration()
    }
}