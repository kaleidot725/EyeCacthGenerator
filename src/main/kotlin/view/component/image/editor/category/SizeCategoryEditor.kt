package view.component.image.editor.category

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.kaleidot725.eyegen.eyegen.generated.resources.Res
import jp.kaleidot725.eyegen.eyegen.generated.resources.category_size
import jp.kaleidot725.eyegen.eyegen.generated.resources.height_title
import jp.kaleidot725.eyegen.eyegen.generated.resources.width_title
import org.jetbrains.compose.resources.stringResource
import view.component.base.TitleText
import view.component.image.editor.value.IntValueEditor

@Composable
fun SizeCategoryEditor(
    width: Int?,
    height: Int?,
    onChangedWidth: (Int?) -> Unit,
    onChangedHeight: (Int?) -> Unit,
) {
    TitleText(stringResource(Res.string.category_size))

    IntValueEditor(
        label = stringResource(Res.string.width_title),
        initialSize = width,
        onChangedSize = onChangedWidth,
        modifier = Modifier.fillMaxWidth(),
    )

    IntValueEditor(
        label = stringResource(Res.string.height_title),
        initialSize = height,
        onChangedSize = onChangedHeight,
        modifier = Modifier.fillMaxWidth(),
    )
}
