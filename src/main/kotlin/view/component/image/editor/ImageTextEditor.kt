package view.component.image.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import jp.kaleidot725.eyegen.eyegen.generated.resources.Res
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_font
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_text
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_text_size
import model.Font
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.ui.component.Dropdown
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField
import view.component.base.ParameterContent
import view.component.base.TitleText

@Composable
fun ImageTextEditor(
    label: String,
    text: String,
    onChangedText: (String) -> Unit,
    font: Font,
    onChangedFont: (Font) -> Unit,
    size: Int?,
    onChangedSize: (Int?) -> Unit,
    allFonts: List<Font>,
    modifier: Modifier = Modifier,
) {
    val textFieldState = rememberTextFieldState(initialText = text)
    LaunchedEffect(textFieldState.text) { onChangedText(textFieldState.text.toString()) }
    val sizeFieldState = rememberTextFieldState(initialText = size?.toString() ?: "")
    LaunchedEffect(sizeFieldState.text) { onChangedSize(sizeFieldState.text.toString().toIntOrNull()) }

    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TitleText(
            text = label,
            modifier = Modifier.fillMaxWidth(),
        )

        ParameterContent(
            label = stringResource(Res.string.text_parameter_text),
        ) {
            TextField(
                state = textFieldState,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        ParameterContent(
            label = stringResource(Res.string.text_parameter_font),
        ) {
            Dropdown(
                menuContent = {
                    allFonts.forEach {
                        selectableItem(
                            selected = it == font,
                            onClick = { onChangedFont(it) },
                        ) {
                            Text(it.value)
                        }
                    }
                },
                content = {
                    Text(font.value)
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        ParameterContent(
            label = stringResource(Res.string.text_parameter_text_size),
        ) {
            TextField(
                state = sizeFieldState,
                inputTransformation = IntOnlyTransformation,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

object IntOnlyTransformation : InputTransformation {
    override val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

    override fun TextFieldBuffer.transformInput() {
        if (this.asCharSequence().toString().isNotEmpty() && this.asCharSequence().toString().toIntOrNull() == null) {
            this.revertAllChanges()
        }
    }
}
