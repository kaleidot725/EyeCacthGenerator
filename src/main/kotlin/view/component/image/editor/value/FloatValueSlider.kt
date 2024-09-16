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
fun FloatValueEditor(
    label: String,
    initValue: Float?,
    onChanged: (Float?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val value = rememberTextFieldState(initialText = initValue?.toString() ?: "")
    LaunchedEffect(value.text) { onChanged(value.text.toString().toFloatOrNull()) }

    ParameterContent(
        label = label,
        modifier = modifier,
    ) {
        TextField(
            state = value,
            inputTransformation = FloatOnlyTransformation,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

private object FloatOnlyTransformation : InputTransformation {
    override val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

    override fun TextFieldBuffer.transformInput() {
        if (this.asCharSequence().toString().isNotEmpty() && this.asCharSequence().toString().toFloatOrNull() == null) {
            this.revertAllChanges()
        }
    }
}
