package view.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.Divider
import org.jetbrains.jewel.ui.component.Text

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        Text(
            text = text,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Divider(
            orientation = Orientation.Horizontal,
            color = JewelTheme.globalColors.borders.normal,
            modifier = Modifier.align(Alignment.CenterVertically).padding(start = 4.dp)
        )
    }
}