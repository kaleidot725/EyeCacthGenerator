package view.component.image.editor.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.kaleidot725.eyegen.eyegen.generated.resources.Res
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_font
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_text
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_text_color
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_text_size
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_text_x
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_text_y
import model.Font
import model.params.TitleParameter
import org.jetbrains.compose.resources.stringResource
import view.component.base.TitleText
import view.component.image.editor.value.ColorEditor
import view.component.image.editor.value.FloatValueEditor
import view.component.image.editor.value.FontEditor
import view.component.image.editor.value.IntValueEditor
import view.component.image.editor.value.TextEditor

@Composable
fun TitleCategoryEditor(
    label: String,
    allFonts: List<Font>,
    title: TitleParameter,
    onChangedText: (String) -> Unit,
    onChangedFont: (Font) -> Unit,
    onChangedSize: (Int?) -> Unit,
    onChangedColor: (ULong?) -> Unit,
    onChangedXPosition: (Float?) -> Unit,
    onChangedYPosition: (Float?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TitleText(
            text = label,
            modifier = Modifier.fillMaxWidth(),
        )

        TextEditor(
            label = stringResource(Res.string.text_parameter_text),
            initialText = title.text,
            onChangedText = onChangedText,
        )

        FontEditor(
            label = stringResource(Res.string.text_parameter_font),
            fonts = allFonts,
            selectedFont = title.font,
            onChangedFont = onChangedFont,
        )

        IntValueEditor(
            label = stringResource(Res.string.text_parameter_text_size),
            initialSize = title.size,
            onChangedSize = onChangedSize,
        )

        ColorEditor(
            label = stringResource(Res.string.text_parameter_text_color),
            initialColor = title.color,
            onChangedColor = onChangedColor,
        )

        FloatValueEditor(
            label = stringResource(Res.string.text_parameter_text_x),
            initValue = title.position.x,
            onChanged = onChangedXPosition
        )

        FloatValueEditor(
            label = stringResource(Res.string.text_parameter_text_y),
            initValue = title.position.y,
            onChanged = onChangedYPosition
        )
    }
}
