package view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.foundation.theme.JewelTheme
import view.component.image.ImagePreview
import view.component.image.editor.ImageEditor

@Composable
fun MainScreen(
    state: MainState,
    onEvent: (MainEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        ImagePreview(
            file = state.previewFile,
            date = state.previewUpdate,
            isLoading = state.isLoading,
            modifier =
                Modifier
                    .fillMaxHeight()
                    .weight(1.0f),
        )
        Spacer(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(JewelTheme.globalColors.borders.normal),
        )
        ImageEditor(
            parameters = state.parameters,
            onChangedTitle = { onEvent(MainEvent.ChangeTitle(it)) },
            onChangedTitleFont = { onEvent(MainEvent.ChangeTitleFont(it)) },
            onChangedTitleSize = { onEvent(MainEvent.ChangeTitleSize(it)) },
            onChangedSubTitle = { onEvent(MainEvent.ChangeSubTitle(it)) },
            onChangedSubTitleFont = { onEvent(MainEvent.ChangeSubTitleFont(it)) },
            onChangedSubTitleSize = { onEvent(MainEvent.ChangeSubTitleSize(it)) },
            onChangedWidth = { onEvent(MainEvent.ChangeWidth(it)) },
            onChangedHeight = { onEvent(MainEvent.ChangeHeight(it)) },
            onChangedStartColor = { onEvent(MainEvent.ChangeStartColor(it)) },
            onChangedEndColor = { onEvent(MainEvent.ChangeEndColor(it)) },
            onSave = { onEvent(MainEvent.Save) },
            allFonts = state.fonts,
            modifier = Modifier.fillMaxHeight().width(400.dp),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MainScreen(
        state = MainState.initValue,
        onEvent = {},
    )
}
