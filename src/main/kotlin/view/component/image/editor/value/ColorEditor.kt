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
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndSelectAll
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

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun ColorEditor(
    label: String,
    initialColor: ULong?,
    onChangedColor: (ULong?) -> Unit,
    modifier: Modifier = Modifier,
) {
    var popup by remember { mutableStateOf(false) }
    var currentColor by remember {
        mutableStateOf(
            initialColor?.let { Color(it) } ?: Color.Red
        )
    }

    val textState = rememberTextFieldState(currentColor.toRgbaHexString())
    LaunchedEffect(textState.text) {
        try {
            val text = textState.text.toString()
            val color = hexToColor(text)
            currentColor = color
            onChangedColor(color.value)
        } catch (e: Exception) {
            println("Error $e")
        }
    }

    ParameterContent(
        label = label,
        modifier = modifier,
    ) {
        TextField(
            state = textState,
            trailingIcon = {
                Box(
                    modifier =
                    Modifier
                        .clickable { popup = true }
                        .size(20.dp)
                        .background(currentColor, CircleShape),
                )

                Popup(
                    onDismissRequest = { popup = false },
                    offset = IntOffset(x = -10, y = 0),
                ) {
                    if (popup) {
                        ColorPicker(
                            currentColor = currentColor,
                            onChangedColor = {
                                val color = Color(it)
                                textState.setTextAndSelectAll(color.toRgbaHexString())
                            },
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
    currentColor: Color,
    onChangedColor: (ULong) -> Unit,
    modifier: Modifier = Modifier,
) {
    val controller: ColorPickerController = rememberColorPickerController()
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
                initialColor = currentColor,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    onChangedColor(colorEnvelope.color.value)
                },
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

private fun hexToColor(hex: String): Color {
    val cleanedHex = hex.removePrefix("#")

    require(cleanedHex.length == 6 || cleanedHex.length == 8) {
        "Hex color string should be 6 (RRGGBB) or 8 (AARRGGBB) characters long"
    }

    val colorLong = cleanedHex.toLong(16)
    return when (cleanedHex.length) {
        6 -> {
            Color(
                red = ((colorLong shr 16) and 0xFF) / 255f,
                green = ((colorLong shr 8) and 0xFF) / 255f,
                blue = (colorLong and 0xFF) / 255f,
                alpha = 1f
            )
        }

        8 -> {
            Color(
                alpha = ((colorLong shr 24) and 0xFF) / 255f,
                red = ((colorLong shr 16) and 0xFF) / 255f,
                green = ((colorLong shr 8) and 0xFF) / 255f,
                blue = (colorLong and 0xFF) / 255f
            )
        }

        else -> throw IllegalArgumentException("Invalid color format")
    }
}