package view.component.image.editor

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import jp.kaleidot725.eyegen.eyegen.generated.resources.Res
import jp.kaleidot725.eyegen.eyegen.generated.resources.category_color
import jp.kaleidot725.eyegen.eyegen.generated.resources.category_size
import jp.kaleidot725.eyegen.eyegen.generated.resources.end_color_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.height_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.save
import jp.kaleidot725.eyegen.eyegen.generated.resources.start_color_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.subtitle_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.title_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.width_title
import model.Font
import model.Parameters
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField
import org.jetbrains.jewel.ui.util.toRgbaHexString
import view.component.base.ParameterContent
import view.component.base.TitleText

@Composable
fun ImageEditor(
    parameters: Parameters,
    onChangedTitle: (String) -> Unit,
    onChangedTitleFont: (Font) -> Unit,
    onChangedTitleSize: (Int?) -> Unit,
    onChangedSubTitle: (String) -> Unit,
    onChangedSubTitleFont: (Font) -> Unit,
    onChangedSubTitleSize: (Int?) -> Unit,
    onChangedWidth: (Int?) -> Unit,
    onChangedHeight: (Int?) -> Unit,
    onChangedStartColor: (ULong) -> Unit,
    onChangedEndColor: (ULong) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
    allFonts: List<Font>,
) {
    val controller: ColorPickerController = rememberColorPickerController()
    var startColorPopUp by remember { mutableStateOf(false) }
    var endColorPopUp by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ImageTextEditor(
            label = stringResource(Res.string.title_title),
            text = parameters.title,
            onChangedText = onChangedTitle,
            font = parameters.titleFont,
            onChangedFont = onChangedTitleFont,
            size = parameters.titleSize,
            onChangedSize = onChangedTitleSize,
            allFonts = allFonts,
            modifier = Modifier.fillMaxWidth(),
        )

        ImageTextEditor(
            label = stringResource(Res.string.subtitle_title),
            text = parameters.subTitle,
            onChangedText = onChangedSubTitle,
            font = parameters.subTitleFont,
            onChangedFont = onChangedSubTitleFont,
            size = parameters.subTitleSize,
            onChangedSize = onChangedSubTitleSize,
            allFonts = allFonts,
        )

        TitleText(stringResource(Res.string.category_size))

        ParameterContent(
            label = stringResource(Res.string.width_title),
        ) {
            TextField(
                value = parameters.width?.toString() ?: "",
                onValueChange = { onChangedWidth(it.toIntOrNull()) },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        ParameterContent(
            label = stringResource(Res.string.height_title),
        ) {
            TextField(
                value = parameters.height?.toString() ?: "",
                onValueChange = { onChangedHeight(it.toIntOrNull()) },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        TitleText(stringResource(Res.string.category_color))

        ParameterContent(
            label = stringResource(Res.string.start_color_title),
        ) {
            TextField(
                value = Color(parameters.startColor).toRgbaHexString(),
                onValueChange = {},
                trailingIcon = {
                    Box(
                        modifier =
                            Modifier
                                .clickable { startColorPopUp = true }
                                .size(20.dp)
                                .background(Color(parameters.startColor), CircleShape),
                    )

                    Popup(
                        onDismissRequest = { startColorPopUp = false },
                        offset = IntOffset(x = -10, y = 0),
                    ) {
                        if (startColorPopUp) {
                            ColorPicker(
                                initialColor = Color(parameters.startColor),
                                onChangedColor = onChangedStartColor,
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        ParameterContent(
            label = stringResource(Res.string.end_color_title),
        ) {
            TextField(
                value = Color(parameters.endColor).toRgbaHexString(),
                onValueChange = {},
                trailingIcon = {
                    Box(
                        modifier =
                            Modifier
                                .clickable { endColorPopUp = true }
                                .size(20.dp)
                                .background(Color(parameters.endColor), CircleShape),
                    )

                    Popup(
                        onDismissRequest = { endColorPopUp = false },
                        offset = IntOffset(x = -10, y = 0),
                    ) {
                        if (endColorPopUp) {
                            ColorPicker(
                                initialColor = Color(parameters.endColor),
                                onChangedColor = onChangedEndColor,
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.weight(1.0f))

        Row(
            modifier = Modifier.align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
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
        ImageEditor(
            parameters = Parameters.initValue,
            onChangedTitle = {},
            onChangedTitleFont = {},
            onChangedTitleSize = {},
            onChangedSubTitle = {},
            onChangedSubTitleFont = {},
            onChangedSubTitleSize = {},
            onChangedWidth = {},
            onChangedHeight = {},
            onChangedStartColor = {},
            onChangedEndColor = {},
            onSave = {},
            allFonts = emptyList(),
            modifier = Modifier,
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
