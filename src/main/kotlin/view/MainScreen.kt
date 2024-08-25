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
            file = state.previewFile,
            date = state.previewUpdate,
            isLoading = state.isLoading,
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
            title = state.parameters.title,
            onChangedTitle = { onEvent(MainEvent.ChangeTitle(it)) },
            titleFont = state.parameters.titleFont,
            onChangedTitleFont = { onEvent(MainEvent.ChangeTitleFont(it)) },
            subTitle = state.parameters.subTitle,
            onChangedSubTitle = { onEvent(MainEvent.ChangeSubTitle(it)) },
            subTitleFont = state.parameters.subTitleFont,
            onChangedSubTitleFont = { onEvent(MainEvent.ChangeSubTitleFont(it)) },
            width = state.parameters.width,
            onChangedWidth = { onEvent(MainEvent.ChangeWidth(it)) },
            height = state.parameters.height,
            onChangedHeight = { onEvent(MainEvent.ChangeHeight(it)) },
            startColor = state.parameters.startColor,
            onChangedStartColor = { onEvent(MainEvent.ChangeStartColor(it)) },
            endColor = state.parameters.endColor,
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
        onEvent = {}
    )
}