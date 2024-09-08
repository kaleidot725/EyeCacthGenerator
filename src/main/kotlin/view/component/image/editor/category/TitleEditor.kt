package view.component.image.editor.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Font
import view.component.base.TitleText
import view.component.image.editor.value.FontEditor
import view.component.image.editor.value.SizeEditor
import view.component.image.editor.value.TextEditor

@Composable
fun TitleCategoryEditor(
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
    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TitleText(
            text = label,
            modifier = Modifier.fillMaxWidth(),
        )

        TextEditor(
            initialText = text,
            onChangedText = onChangedText,
        )

        FontEditor(
            fonts = allFonts,
            selectedFont = font,
            onChangedFont = onChangedFont,
        )

        SizeEditor(
            initialSize = size,
            onChangedSize = onChangedSize,
        )
    }
}
