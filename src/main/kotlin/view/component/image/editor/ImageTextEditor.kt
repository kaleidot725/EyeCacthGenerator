package view.component.image.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.kaleidot725.eyegen.eyegen.generated.resources.Res
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_font
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_text
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_text_size
import model.Font
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.ui.component.Dropdown
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField
import view.component.base.ParameterContent
import view.component.base.TitleText

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ImageTextEditor(
    label: String,
    text: String,
    onChangedText: (String) -> Unit,
    font: Font,
    onChangedFont: (Font) -> Unit,
    size: Int,
    onChangedSize: (Int) -> Unit,
    allFonts: List<Font>,
    modifier: Modifier = Modifier
) {
    val localText by rememberUpdatedState(text)
    val localSize by rememberUpdatedState(size)

    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TitleText(
            text = label,
            modifier = Modifier.fillMaxWidth()
        )

        ParameterContent(
            label = stringResource(Res.string.text_parameter_text)
        ) {
            TextField(
                value = localText,
                onValueChange = onChangedText,
                modifier = Modifier.fillMaxWidth()
            )
        }

        ParameterContent(
            label = stringResource(Res.string.text_parameter_font)
        ) {
            Dropdown(
                menuContent = {
                    allFonts.forEach {
                        selectableItem(
                            selected = it == font,
                            onClick = { onChangedFont(it) }
                        ) {
                            Text(it.value)
                        }
                    }
                },
                content = {
                    Text(font.value)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        ParameterContent(
            label = stringResource(Res.string.text_parameter_text_size)
        ) {
            // 明日から始めるJetpack Compose入門
            // 基本編
            TextField(
                value = localSize.toString(),
                onValueChange = { onChangedSize(it.toIntOrNull() ?: 0) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

