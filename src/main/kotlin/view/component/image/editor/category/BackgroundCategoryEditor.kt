package view.component.image.editor.category

import androidx.compose.runtime.Composable
import jp.kaleidot725.eyegen.eyegen.generated.resources.Res
import jp.kaleidot725.eyegen.eyegen.generated.resources.category_color
import jp.kaleidot725.eyegen.eyegen.generated.resources.end_color_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.start_color_title
import org.jetbrains.compose.resources.stringResource
import view.component.base.TitleText
import view.component.image.editor.value.ColorEditor

@Composable
fun BackgroundCategoryEditor(
    startColor: ULong?,
    endColor: ULong?,
    onChangedStartColor: (ULong?) -> Unit,
    onChangedEndColor: (ULong?) -> Unit,
) {
    TitleText(stringResource(Res.string.category_color))

    ColorEditor(
        label = stringResource(Res.string.start_color_title),
        initialColor = startColor,
        onChangedColor = onChangedStartColor,
    )

    ColorEditor(
        label = stringResource(Res.string.end_color_title),
        initialColor = endColor,
        onChangedColor = onChangedEndColor,
    )
}
