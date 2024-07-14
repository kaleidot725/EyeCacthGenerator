package view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.Flow
import model.Parameters
import model.Window
import repository.ImageRepository
import repository.WindowRepository
import java.io.File

data class MainState(
    val parameters: Parameters,
    val previewFile: File,
    val window: Window? = null,
    val isExit: Boolean = false
)

sealed interface MainEvent {
    data class ChangeTitle(val value: String) : MainEvent
    data class ChangeSubTitle(val value: String) : MainEvent
    data class ChangeWidth(val value: Int) : MainEvent
    data class ChangeHeight(val value: Int) : MainEvent
    data class ChangeStartColor(val color: ULong) : MainEvent
    data class ChangeEndColor(val color: ULong) : MainEvent
    data object Save : MainEvent
    data class Destroy(val window: Window) : MainEvent
}

@Composable
fun MainProcessor(
    event: Flow<MainEvent>,
    windowRepository: WindowRepository,
    imageRepository: ImageRepository,
): MainState {
    var parameters by remember {
        mutableStateOf(
            Parameters(
                title = "TITLE",
                subTitle = "SubTitle",
                width = 1000,
                height = 1000,
                startColor = Color.Red.value,
                endColor = Color.Blue.value
            )
        )
    }
    var previewFile by remember { mutableStateOf(File("")) }
    val window by remember { mutableStateOf(windowRepository.get()) }
    var isExit by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        event.collect { event ->
            when (event) {
                is MainEvent.ChangeTitle -> {
                    parameters = parameters.copy(title = event.value)
                    previewFile = imageRepository.createPreview(parameters)
                }
                is MainEvent.ChangeSubTitle -> {
                    parameters = parameters.copy(subTitle = event.value)
                    previewFile = imageRepository.createPreview(parameters)
                }
                is MainEvent.ChangeHeight -> {
                    parameters = parameters.copy(height = event.value)
                    previewFile =  imageRepository.createPreview(parameters)
                }
                is MainEvent.ChangeWidth -> {
                    parameters = parameters.copy(width = event.value)
                    previewFile = imageRepository.createPreview(parameters)
                }
                is MainEvent.ChangeStartColor -> {
                    parameters = parameters.copy(startColor = event.color)
                    previewFile = imageRepository.createPreview(parameters)
                }
                is MainEvent.ChangeEndColor -> {
                    parameters = parameters.copy(endColor = event.color)
                    previewFile =  imageRepository.createPreview(parameters)
                }
                is MainEvent.Save -> {
                    imageRepository.createFileToDesktop(parameters)
                }
                is MainEvent.Destroy -> {
                    windowRepository.update(event.window)
                    isExit = true
                }
            }
        }
    }

    return MainState(
        parameters = parameters,
        previewFile = previewFile,
        window = window,
        isExit = isExit
    )
}