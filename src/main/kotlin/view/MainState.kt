package view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import model.Font
import model.Parameters
import model.Window
import repository.FontRepository
import repository.ImageRepository
import repository.WindowRepository
import java.io.File
import java.util.Date

data class MainState(
    val parameters: Parameters,
    val previewFile: File,
    val previewUpdate: Long,
    val isLoading: Boolean,
    val fonts: List<Font>,
    val window: Window? = null,
    val isExit: Boolean = false
) {
    companion object {
        val initValue = MainState(
            parameters = Parameters.initValue,
            previewFile = File(""),
            previewUpdate = Date().time,
            isLoading = false,
            fonts = emptyList(),
            isExit = false,
            window = Window()
        )
    }
}

sealed interface MainEvent {
    data class ChangeTitle(val value: String) : MainEvent
    data class ChangeTitleFont(val value: Font) : MainEvent
    data class ChangeTitleSize(val value: Int) : MainEvent
    data class ChangeSubTitle(val value: String) : MainEvent
    data class ChangeSubTitleFont(val value: Font) : MainEvent
    data class ChangeSubTitleSize(val value: Int) : MainEvent
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
    fontRepository: FontRepository,
): MainState {
    val scope = rememberCoroutineScope()
    var parameters by remember { mutableStateOf(Parameters.initValue) }
    var previewFile by remember { mutableStateOf(File("")) }
    var previewUpdate by remember { mutableStateOf(0L) }
    var previewJob by remember { mutableStateOf<Job?>(null) }
    val window by remember { mutableStateOf(windowRepository.get()) }
    var isExit by remember { mutableStateOf(false) }
    var fonts by remember { mutableStateOf(emptyList<Font>()) }

    fun createPreviewFile() {
        previewJob?.cancel()
        previewJob = scope.launch {
            delay(100)
            previewFile = imageRepository.createPreview(parameters)
            previewUpdate = Date().time
            previewJob = null
        }
    }

    LaunchedEffect(Unit) {
        createPreviewFile()
        event.collect { event ->
            when (event) {
                is MainEvent.ChangeTitle -> {
                    parameters = parameters.copy(title = event.value)
                    createPreviewFile()
                }

                is MainEvent.ChangeTitleFont -> {
                    parameters = parameters.copy(titleFont = event.value)
                    createPreviewFile()
                }

                is MainEvent.ChangeTitleSize -> {
                    parameters = parameters.copy(titleSize = event.value)
                    createPreviewFile()
                }

                is MainEvent.ChangeSubTitle -> {
                    parameters = parameters.copy(subTitle = event.value)
                    createPreviewFile()
                }

                is MainEvent.ChangeSubTitleFont -> {
                    parameters = parameters.copy(subTitleFont = event.value)
                    createPreviewFile()
                }

                is MainEvent.ChangeSubTitleSize -> {
                    parameters = parameters.copy(subTitleSize = event.value)
                    createPreviewFile()
                }

                is MainEvent.ChangeHeight -> {
                    parameters = parameters.copy(height = event.value)
                    createPreviewFile()
                }

                is MainEvent.ChangeWidth -> {
                    parameters = parameters.copy(width = event.value)
                    createPreviewFile()
                }

                is MainEvent.ChangeStartColor -> {
                    parameters = parameters.copy(startColor = event.color)
                    createPreviewFile()
                }

                is MainEvent.ChangeEndColor -> {
                    parameters = parameters.copy(endColor = event.color)
                    createPreviewFile()
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

    LaunchedEffect(Unit) {
        fonts = fontRepository.get()
    }

    return MainState(
        parameters = parameters,
        previewFile = previewFile,
        previewUpdate = previewUpdate,
        isLoading = previewJob != null,
        fonts = fonts,
        window = window,
        isExit = isExit
    )
}