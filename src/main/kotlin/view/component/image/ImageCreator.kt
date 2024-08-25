package view.component.image

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
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
import jp.kaleidot725.eyegen.eyegen.generated.resources.end_color_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.height_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.save
import jp.kaleidot725.eyegen.eyegen.generated.resources.start_color_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.subtitle_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.title_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.width_title
import model.Font
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField
import org.jetbrains.jewel.ui.util.toRgbaHexString
import view.component.base.ParameterContent
import view.component.base.TitleText

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ImageCreator(
    title: String,
    onChangedTitle: (String) -> Unit,
    titleFont: Font,
    onChangedTitleFont: (Font) -> Unit,
    subTitle: String,
    onChangedSubTitle: (String) -> Unit,
    subTitleFont: Font,
    onChangedSubTitleFont: (Font) -> Unit,
    width: Int,
    onChangedWidth: (Int) -> Unit,
    height: Int,
    onChangedHeight: (Int) -> Unit,
    startColor: ULong,
    onChangedStartColor: (ULong) -> Unit,
    endColor: ULong,
    onChangedEndColor: (ULong) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
    allFonts: List<Font>
) {
    val controller: ColorPickerController = rememberColorPickerController()
    var selectedStartColor by remember { mutableStateOf(false) }
    var selectedEndColor by remember { mutableStateOf(false) }

    LaunchedEffect(selectedStartColor, selectedEndColor) {
        controller.enabled = (selectedStartColor || selectedEndColor)
    }

    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ImageTextEditor(
            label = stringResource(Res.string.title_title),
            text = title,
            onChangedText = onChangedTitle,
            font = titleFont,
            onChangedFont = onChangedTitleFont,
            allFonts = allFonts,
            modifier = Modifier.fillMaxWidth()
        )

        ImageTextEditor(
            label = stringResource(Res.string.subtitle_title),
            text = subTitle,
            onChangedText = onChangedSubTitle,
            font = subTitleFont,
            onChangedFont = onChangedSubTitleFont,
            allFonts = allFonts
        )

        TitleText(stringResource(Res.string.category_size))

        ParameterContent(
            label = stringResource(Res.string.width_title)
        ) {
            TextField(
                value = width.toString(),
                onValueChange = { onChangedWidth(it.toIntOrNull() ?: 0) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        ParameterContent(
            label = stringResource(Res.string.height_title)
        ) {
            TextField(
                value = height.toString(),
                onValueChange = { onChangedHeight(it.toIntOrNull() ?: 0) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        TitleText(stringResource(Res.string.category_color))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color(startColor), CircleShape)
                    .align(Alignment.CenterVertically)
            )

            Text(
                text = stringResource(Res.string.start_color_title),
                modifier = Modifier.width(125.dp).align(Alignment.CenterVertically)
            )

            TextField(
                value = Color(startColor).toRgbaHexString(),
                onValueChange = {},
                modifier = Modifier
                    .weight(1.0f)
                    .align(Alignment.CenterVertically)
                    .onFocusChanged { state ->
                        selectedStartColor = state.hasFocus
                        if (selectedStartColor) controller.selectByColor(Color(startColor), true)
                    }
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color(endColor), CircleShape)
                    .align(Alignment.CenterVertically)
            )

            Text(
                text = stringResource(Res.string.end_color_title),
                modifier = Modifier.width(125.dp).align(Alignment.CenterVertically)
            )

            TextField(
                value = Color(endColor).toRgbaHexString(),
                onValueChange = {},
                modifier = Modifier
                    .weight(1.0f)
                    .align(Alignment.CenterVertically)
                    .onFocusChanged { state ->
                        selectedEndColor = state.hasFocus
                        if (selectedEndColor) controller.selectByColor(Color(endColor), true)
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
                    onChangedStartColor(colorEnvelope.color.value)
                }
                if (selectedEndColor) {
                    onChangedEndColor(colorEnvelope.color.value)
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
            titleFont = Font("TEST"),
            onChangedTitleFont = {},
            subTitle = "",
            onChangedSubTitle = {},
            subTitleFont = Font("TEST"),
            onChangedSubTitleFont = {},
            width = 0,
            onChangedWidth = {},
            height = 0,
            onChangedHeight = {},
            startColor = Color.Red.value,
            onChangedStartColor = {},
            endColor = Color.Blue.value,
            onChangedEndColor = {},
            onSave = {},
            allFonts = emptyList(),
            modifier = Modifier
        )
    }
}