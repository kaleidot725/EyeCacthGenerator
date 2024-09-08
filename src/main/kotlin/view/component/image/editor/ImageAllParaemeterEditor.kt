package view.component.image.editor

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.kaleidot725.eyegen.eyegen.generated.resources.Res
import jp.kaleidot725.eyegen.eyegen.generated.resources.category_color
import jp.kaleidot725.eyegen.eyegen.generated.resources.category_size
import jp.kaleidot725.eyegen.eyegen.generated.resources.end_color_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.height_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.save
import jp.kaleidot725.eyegen.eyegen.generated.resources.start_color_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.subtitle_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.title_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.width_title
import model.Font
import model.params.Parameters
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Text
import view.MainEvent
import view.component.base.TitleText
import view.component.image.editor.category.TitleCategoryEditor
import view.component.image.editor.value.ColorEditor
import view.component.image.editor.value.SizeEditor

@Composable
fun ImageEditor(
    parameters: Parameters,
    onEvent: (MainEvent) -> Unit,
    modifier: Modifier = Modifier,
    allFonts: List<Font>,
) {
    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TitleCategoryEditor(
            allFonts = allFonts,
            label = stringResource(Res.string.title_title),
            title = parameters.title,
            onChangedText = { onEvent(MainEvent.ChangeTitle(it)) },
            onChangedFont = { onEvent(MainEvent.ChangeTitleFont(it)) },
            onChangedSize = { onEvent(MainEvent.ChangeTitleSize(it)) },
            onChangedColor = { onEvent(MainEvent.ChangeTitleColor(it)) },
            modifier = Modifier.fillMaxWidth(),
        )

        TitleCategoryEditor(
            allFonts = allFonts,
            label = stringResource(Res.string.subtitle_title),
            title = parameters.subTitle,
            onChangedText = { onEvent(MainEvent.ChangeSubTitle(it)) },
            onChangedFont = { onEvent(MainEvent.ChangeSubTitleFont(it)) },
            onChangedSize = { onEvent(MainEvent.ChangeSubTitleSize(it)) },
            onChangedColor = { onEvent(MainEvent.ChangeSubTitleColor(it)) },
            modifier = Modifier.fillMaxWidth(),
        )

        TitleText(stringResource(Res.string.category_size))

        SizeEditor(
            label = stringResource(Res.string.width_title),
            initialSize = parameters.width,
            onChangedSize = { onEvent(MainEvent.ChangeWidth(it)) },
            modifier = Modifier.fillMaxWidth(),
        )

        SizeEditor(
            label = stringResource(Res.string.height_title),
            initialSize = parameters.height,
            onChangedSize = { onEvent(MainEvent.ChangeHeight(it)) },
            modifier = Modifier.fillMaxWidth(),
        )

        TitleText(stringResource(Res.string.category_color))

        ColorEditor(
            label = stringResource(Res.string.start_color_title),
            initialColor = parameters.startColor,
            onChangedColor = { onEvent(MainEvent.ChangeStartColor(it)) },
        )

        ColorEditor(
            label = stringResource(Res.string.end_color_title),
            initialColor = parameters.endColor,
            onChangedColor = { onEvent(MainEvent.ChangeEndColor(it)) },
        )

        Spacer(modifier = Modifier.weight(1.0f))

        Row(
            modifier = Modifier.align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            DefaultButton(onClick = { onEvent(MainEvent.Save) }) {
                Text(stringResource(Res.string.save))
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.size(500.dp)) {
        ImageEditor(
            parameters = Parameters.initValue,
            onEvent = {},
            allFonts = emptyList(),
            modifier = Modifier,
        )
    }
}
