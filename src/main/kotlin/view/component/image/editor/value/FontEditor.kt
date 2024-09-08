package view.component.image.editor.value

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.kaleidot725.eyegen.eyegen.generated.resources.Res
import jp.kaleidot725.eyegen.eyegen.generated.resources.text_parameter_font
import model.Font
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.ui.component.Dropdown
import org.jetbrains.jewel.ui.component.Text
import view.component.base.ParameterContent

@Composable
fun FontEditor(
    selectedFont: Font,
    fonts: List<Font>,
    onChangedFont: (Font) -> Unit,
    modifier: Modifier = Modifier,
) {
    ParameterContent(
        modifier = modifier,
        label = stringResource(Res.string.text_parameter_font),
    ) {
        Dropdown(
            menuContent = {
                fonts.forEach {
                    selectableItem(
                        selected = it == selectedFont,
                        onClick = { onChangedFont(it) },
                    ) {
                        Text(it.value)
                    }
                }
            },
            content = {
                Text(selectedFont.value)
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    val fonts =
        listOf(
            Font("TEST1"),
            Font("TEST2"),
            Font("TEST3"),
            Font("TEST4"),
            Font("TEST5"),
        )

    FontEditor(
        selectedFont = fonts.first(),
        fonts = fonts,
        onChangedFont = {},
    )
}
