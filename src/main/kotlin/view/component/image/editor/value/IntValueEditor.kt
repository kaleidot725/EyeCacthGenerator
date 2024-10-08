package view.component.image.editor.value

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import org.jetbrains.jewel.ui.component.TextField
import view.component.base.ParameterContent

@Composable
fun IntValueEditor(
    label: String,
    initialSize: Int?,
    onChangedSize: (Int?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val size = rememberTextFieldState(initialText = initialSize?.toString() ?: "")
    LaunchedEffect(size.text) { onChangedSize(size.text.toString().toIntOrNull()) }

    ParameterContent(
        label = label,
        modifier = modifier,
    ) {
        TextField(
            state = size,
            inputTransformation = IntOnlyTransformation,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

private object IntOnlyTransformation : InputTransformation {
    override val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

    override fun TextFieldBuffer.transformInput() {
        if (this.asCharSequence().toString().isNotEmpty() && this.asCharSequence().toString().toIntOrNull() == null) {
            this.revertAllChanges()
        }
    }
}
