import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ImageConfiguration(
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(text = "CONFIGURATION SAMPLE", modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.size(500.dp)) {
        ImageConfiguration()
    }
}