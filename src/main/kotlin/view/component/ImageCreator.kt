package view.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import jp.kaleidot725.eyegen.eyegen.generated.resources.Res
import jp.kaleidot725.eyegen.eyegen.generated.resources.category_color
import jp.kaleidot725.eyegen.eyegen.generated.resources.category_size
import jp.kaleidot725.eyegen.eyegen.generated.resources.category_text
import jp.kaleidot725.eyegen.eyegen.generated.resources.end_color_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.height_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.save
import jp.kaleidot725.eyegen.eyegen.generated.resources.start_color_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.subtitle_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.title_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.width_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField
import org.jetbrains.jewel.ui.util.toRgbaHexString

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ImageCreator(
    title: String,
    onChangedTitle: (String) -> Unit,
    subTitle: String,
    onChangedSubTitle: (String) -> Unit,
    width: Int,
    onChangedWidth: (Int) -> Unit,
    height: Int,
    onChangedHeight: (Int) -> Unit,
    startColor: Color,
    onChangedStartColor: (Color) -> Unit,
    endColor: Color,
    onChangedEndColor: (Color) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val controller : ColorPickerController = rememberColorPickerController()
    var selectedStartColor by remember { mutableStateOf(false) }
    var selectedEndColor by remember { mutableStateOf(false) }

    LaunchedEffect(selectedStartColor, selectedEndColor) {
        controller.enabled = (selectedStartColor || selectedEndColor)
    }

    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TitleText(stringResource(Res.string.category_text))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.title_title),
                modifier = Modifier.width(125.dp).align(Alignment.CenterVertically)
            )

            TextField(
                value = title,
                onValueChange = onChangedTitle,
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.subtitle_title),
                modifier = Modifier.width(125.dp).align(Alignment.CenterVertically)
            )

            TextField(
                value = subTitle,
                onValueChange = onChangedSubTitle,
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            )
        }

        TitleText(stringResource(Res.string.category_size))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.width_title),
                modifier = Modifier.width(125.dp).align(Alignment.CenterVertically)
            )

            TextField(
                value = width.toString(),
                onValueChange = { onChangedWidth(it.toIntOrNull() ?: 0) },
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.height_title),
                modifier = Modifier.width(125.dp).align(Alignment.CenterVertically)
            )

            TextField(
                value = height.toString(),
                onValueChange = { onChangedHeight(it.toIntOrNull() ?: 0)},
                modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
            )
        }

        TitleText(stringResource(Res.string.category_color))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier
                .size(20.dp)
                .background(startColor, CircleShape)
                .align(Alignment.CenterVertically)
            )

            Text(
                text = stringResource(Res.string.start_color_title),
                modifier = Modifier.width(125.dp).align(Alignment.CenterVertically)
            )

            TextField(
                value = startColor.toRgbaHexString(),
                onValueChange = {},
                modifier = Modifier
                    .weight(1.0f)
                    .align(Alignment.CenterVertically)
                    .onFocusChanged { state ->
                        selectedStartColor = state.hasFocus
                        if (selectedStartColor) controller.selectByColor(startColor, true)
                    }
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier
                .size(20.dp)
                .background(endColor, CircleShape)
                .align(Alignment.CenterVertically)
            )

            Text(
                text = stringResource(Res.string.end_color_title),
                modifier = Modifier.width(125.dp).align(Alignment.CenterVertically)
            )

            TextField(
                value = endColor.toRgbaHexString(),
                onValueChange = {},
                modifier = Modifier
                    .weight(1.0f)
                    .align(Alignment.CenterVertically)
                    .onFocusChanged { state ->
                        selectedEndColor = state.hasFocus
                        if (selectedEndColor) controller.selectByColor(endColor, true)
                    }
            )
        }

        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(10.dp),
            controller = controller,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                if (selectedStartColor) {
                    onChangedStartColor(colorEnvelope.color)
                }
                if (selectedEndColor) {
                    onChangedEndColor(colorEnvelope.color)
                }
            },
            initialColor = Color.Red
        )

        AlphaSlider(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp),
            controller = controller
        )

        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp),
            controller = controller
        )

        Spacer(modifier = Modifier.weight(1.0f))

        Row(
            modifier = Modifier.align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DefaultButton(onClick = onSave) {
                Text(stringResource(Res.string.save))
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.size(500.dp)) {
        ImageCreator(
            title = "",
            onChangedTitle = {},
            subTitle = "",
            onChangedSubTitle = {},
            width = 0,
            onChangedWidth = {},
            height = 0,
            onChangedHeight = {},
            startColor = Color.Red,
            onChangedStartColor = {},
            endColor = Color.Blue,
            onChangedEndColor = {},
            onSave = {},
            modifier = Modifier
        )
    }
}