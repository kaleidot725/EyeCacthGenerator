package view.component.image.editor.value

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import org.jetbrains.jewel.ui.component.TextField
import view.component.base.ParameterContent

@Composable
fun TextEditor(
    label: String,
    initialText: String,
    onChangedText: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textFieldState = rememberTextFieldState(initialText = initialText)
    LaunchedEffect(textFieldState.text) { onChangedText(textFieldState.text.toString()) }

    ParameterContent(
        modifier = modifier,
        label = label,
    ) {
        TextField(
            state = textFieldState,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
