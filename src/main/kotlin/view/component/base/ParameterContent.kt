package view.component.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ParameterContent(
    modifier: Modifier = Modifier,
    label: String,
    content: @Composable BoxScope.() -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.width(125.dp).align(Alignment.CenterVertically)
        )

        Box(
            modifier = Modifier.weight(1.0f).align(Alignment.CenterVertically)
        ) {
            content()
        }
    }
}