package view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Font
import org.jetbrains.jewel.ui.component.Dropdown
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField

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
            label = "Text"
        ) {
            TextField(
                value = text,
                onValueChange = onChangedText,
                modifier = Modifier.fillMaxWidth()
            )
        }

        ParameterContent(
            label = "Font"
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

