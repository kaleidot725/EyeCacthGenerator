package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.component.HorizontalSplitLayout
import view.component.image.ImagePreview
import view.component.image.editor.AllParameterEditor

@Composable
fun MainScreen(
    state: MainState,
    onEvent: (MainEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    HorizontalSplitLayout(
        first = {
            ImagePreview(
                file = state.previewFile,
                date = state.previewUpdate,
                isLoading = state.isLoading,
                modifier =
                    Modifier.fillMaxHeight().fillMaxWidth(),
            )
        },
        second = {
            AllParameterEditor(
                parameters = state.parameters,
                onEvent = onEvent,
                allFonts = state.fonts,
                modifier = Modifier.fillMaxHeight(),
            )
        },
        firstPaneMinWidth = 1000.dp,
        secondPaneMinWidth = 400.dp,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun Preview() {
    MainScreen(
        state = MainState.initValue,
        onEvent = {},
    )
}
