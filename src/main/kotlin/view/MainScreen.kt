package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.jetbrains.jewel.foundation.theme.JewelTheme
import view.component.ImageCreator
import view.component.ImagePreview

@Composable
fun MainScreen(
    state: MainState,
    onEvent: (MainEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
    ) {
        ImagePreview(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1.0f)
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(JewelTheme.globalColors.borders.normal)
        )
        ImageCreator(
            title = state.title,
            onChangedTitle = {
                onEvent(MainEvent.ChangeTitle(it))
                             },
            subTitle = state.subTitle,
            onChangedSubTitle = {onEvent(MainEvent.ChangeSubTitle(it)) },
            width = state.width,
            onChangedWidth = { onEvent(MainEvent.ChangeWidth(it)) },
            height = state.height,
            onChangedHeight =  { onEvent(MainEvent.ChangeHeight(it)) },
            startColor = state.startColor,
            onChangedStartColor = { onEvent(MainEvent.ChangeStartColor(it)) },
            endColor = state.endColor,
            onChangedEndColor = { onEvent(MainEvent.ChangeEndColor(it)) },
            onSave = { onEvent(MainEvent.Save) },
            modifier = Modifier.fillMaxHeight().width(400.dp),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MainScreen(
        state = MainState(
            title = "Hello World",
            subTitle = "Hello World",
            width = 1920,
            height = 1080,
            startColor = Color.Red,
            endColor = Color.Blue
        ),
        onEvent = {}
    )
}