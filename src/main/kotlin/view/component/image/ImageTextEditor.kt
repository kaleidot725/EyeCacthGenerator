package view.component.image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.kaleidot725.eyegen.eyegen.generated.resources.Res
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_font
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_text
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
    allFonts: List<Font>,
    modifier: Modifier = Modifier
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TitleText(
            text = label,
            modifier = Modifier.fillMaxWidth()
        )

        ParameterContent(
            label = stringResource(Res.string.text_parameter_text)
        ) {
            TextField(
                value = text,
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
    }
}

