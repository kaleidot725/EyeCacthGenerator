package view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.Flow

data class MainState(
    val title: String = "",
    val subTitle: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val startColor: Color = Color.Transparent,
    val endColor: Color = Color.Transparent,
)

sealed interface MainEvent {
    data class ChangeTitle(val value: String) : MainEvent
    data class ChangeSubTitle(val value: String) : MainEvent
    data class ChangeWidth(val value: Int) : MainEvent
    data class ChangeHeight(val  value: Int) : MainEvent
    data class ChangeStartColor(val value: Color) : MainEvent
    data class ChangeEndColor(val value: Color) : MainEvent
    data object Save: MainEvent
}

@Composable
fun MainProcessor(event: Flow<MainEvent>): MainState {
    var title by remember { mutableStateOf("Hello World") }
    var subTitle by remember { mutableStateOf("Hello World") }
    var width by remember { mutableStateOf(1920) }
    var height by remember { mutableStateOf(1080) }
    var startColor by remember { mutableStateOf(Color.Red) }
    var endColor by remember { mutableStateOf(Color.Blue) }

    LaunchedEffect(Unit) {
        event.collect { event ->
            when (event) {
                is MainEvent.ChangeTitle -> title = event.value
                is MainEvent.ChangeSubTitle -> subTitle = event.value
                is MainEvent.ChangeHeight -> height = event.value
                is MainEvent.ChangeWidth -> width = event.value
                is MainEvent.ChangeStartColor -> startColor = event.value
                is MainEvent.ChangeEndColor -> endColor = event.value
                is MainEvent.Save -> {

                }
            }
        }
    }

    return MainState(
        title = title,
        subTitle = subTitle,
        width = width,
        height = height,
        startColor = startColor,
        endColor = endColor
    )
}