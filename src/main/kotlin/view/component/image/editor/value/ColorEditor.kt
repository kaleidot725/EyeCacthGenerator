package view.component.image.editor.value

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.TextField
import org.jetbrains.jewel.ui.util.toRgbaHexString
import view.component.base.ParameterContent

@Composable
fun ColorEditor(
    label: String,
    initialColor: ULong?,
    onChangedColor: (ULong?) -> Unit,
    modifier: Modifier = Modifier,
) {
    var startColorPopUp by remember { mutableStateOf(false) }
    val startColor by remember(initialColor) {
        mutableStateOf(
            TextFieldState(initialColor?.let { Color(it).toRgbaHexString() } ?: ""),
        )
    }

    ParameterContent(
        label = label,
        modifier = modifier,
    ) {
        val color = Color(initialColor ?: Color.Transparent.value)
        TextField(
            state = startColor,
            trailingIcon = {
                Box(
                    modifier =
                        Modifier
                            .clickable { startColorPopUp = true }
                            .size(20.dp)
                            .background(color, CircleShape),
                )

                Popup(
                    onDismissRequest = { startColorPopUp = false },
                    offset = IntOffset(x = -10, y = 0),
                ) {
                    if (startColorPopUp) {
                        ColorPicker(
                            initialColor = color,
                            onChangedColor = onChangedColor,
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ColorPicker(
    initialColor: Color,
    onChangedColor: (ULong) -> Unit,
    modifier: Modifier = Modifier,
) {
    val controller: ColorPickerController = rememberColorPickerController()
    LaunchedEffect(Unit) { controller.selectByColor(initialColor, false) }

    Box(modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.shadow(8.dp).background(JewelTheme.globalColors.panelBackground).padding(12.dp),
        ) {
            HsvColorPicker(
                modifier =
                    Modifier
                        .width(250.dp)
                        .height(200.dp)
                        .padding(10.dp),
                controller = controller,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    if (initialColor != colorEnvelope.color) {
                        onChangedColor(colorEnvelope.color.value)
                    }
                },
                initialColor = Color.Red,
            )

            AlphaSlider(
                modifier =
                    Modifier
                        .width(250.dp)
                        .height(35.dp),
                controller = controller,
            )

            BrightnessSlider(
                modifier =
                    Modifier
                        .width(250.dp)
                        .height(35.dp),
                controller = controller,
            )
        }
    }
}
