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
import model.Window
import model.params.Parameters
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
    val isExit: Boolean = false,
) {
    companion object {
        val Null =
            MainState(
                parameters = Parameters.initValue,
                previewFile = File(""),
                previewUpdate = Date().time,
                isLoading = false,
                fonts = emptyList(),
                isExit = false,
                window = Window(),
            )
    }
}

sealed interface MainEvent {
    data class ChangeTitle(
        val value: String,
    ) : MainEvent

    data class ChangeTitleFont(
        val value: Font,
    ) : MainEvent

    data class ChangeTitleSize(
        val value: Int?,
    ) : MainEvent

    data class ChangeTitleColor(
        val value: ULong?,
    ) : MainEvent

    data class ChangeTitleX(
        val value: Float?,
    ) : MainEvent

    data class ChangeTitleY(
        val value: Float?,
    ) : MainEvent

    data class ChangeSubTitle(
        val value: String,
    ) : MainEvent

    data class ChangeSubTitleFont(
        val value: Font,
    ) : MainEvent

    data class ChangeSubTitleSize(
        val value: Int?,
    ) : MainEvent

    data class ChangeSubTitleColor(
        val value: ULong?,
    ) : MainEvent

    data class ChangeSubTitleX(
        val value: Float?,
    ) : MainEvent

    data class ChangeSubTitleY(
        val value: Float?,
    ) : MainEvent

    data class ChangeWidth(
        val value: Int?,
    ) : MainEvent

    data class ChangeHeight(
        val value: Int?,
    ) : MainEvent

    data class ChangeStartColor(
        val color: ULong?,
    ) : MainEvent

    data class ChangeEndColor(
        val color: ULong?,
    ) : MainEvent

    data object Save : MainEvent

    data class Destroy(
        val parameters: Parameters,
        val window: Window,
    ) : MainEvent
}

@Composable
fun MainProcessor(
    event: Flow<MainEvent>,
    windowRepository: WindowRepository,
    imageRepository: ImageRepository,
    fontRepository: FontRepository,
): MainState {
    println(imageRepository.localParameters)

    val scope = rememberCoroutineScope()
    var parameters by remember { mutableStateOf(imageRepository.localParameters) }
    var previewFile by remember { mutableStateOf(File("")) }
    var previewUpdate by remember { mutableStateOf(0L) }
    var previewJob by remember { mutableStateOf<Job?>(null) }
    val window by remember { mutableStateOf(windowRepository.get()) }
    var isExit by remember { mutableStateOf(false) }
    var fonts by remember { mutableStateOf(emptyList<Font>()) }

    fun createPreviewFile() {
        previewJob?.cancel()
        previewJob =
            scope.launch {
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
                    parameters =
                        parameters.copy(
                            title = parameters.title.copy(text = event.value),
                        )
                    createPreviewFile()
                }

                is MainEvent.ChangeTitleFont -> {
                    parameters =
                        parameters.copy(
                            title = parameters.title.copy(font = event.value),
                        )
                    createPreviewFile()
                }

                is MainEvent.ChangeTitleSize -> {
                    parameters =
                        parameters.copy(
                            title = parameters.title.copy(size = event.value),
                        )
                    createPreviewFile()
                }

                is MainEvent.ChangeTitleColor -> {
                    parameters =
                        parameters.copy(
                            title = parameters.title.copy(color = event.value),
                        )
                    createPreviewFile()
                }

                is MainEvent.ChangeTitleX -> {
                    parameters =
                        parameters.copy(
                            title = parameters.title.copy(
                                position = parameters.title.position.copy(
                                    x = event.value ?: parameters.title.position.x
                                )
                            ),
                        )
                    createPreviewFile()
                }

                is MainEvent.ChangeTitleY -> {
                    parameters =
                        parameters.copy(
                            title = parameters.title.copy(
                                position = parameters.title.position.copy(
                                    y = event.value ?: parameters.title.position.y
                                )
                            ),
                        )
                    createPreviewFile()
                }

                is MainEvent.ChangeSubTitle -> {
                    parameters =
                        parameters.copy(
                            subTitle = parameters.subTitle.copy(text = event.value),
                        )
                    createPreviewFile()
                }

                is MainEvent.ChangeSubTitleFont -> {
                    parameters =
                        parameters.copy(
                            subTitle = parameters.subTitle.copy(font = event.value),
                        )
                    createPreviewFile()
                }

                is MainEvent.ChangeSubTitleSize -> {
                    parameters =
                        parameters.copy(
                            subTitle = parameters.subTitle.copy(size = event.value),
                        )
                    createPreviewFile()
                }

                is MainEvent.ChangeSubTitleColor -> {
                    parameters =
                        parameters.copy(
                            subTitle = parameters.subTitle.copy(color = event.value),
                        )
                    createPreviewFile()
                }

                is MainEvent.ChangeSubTitleX -> {
                    parameters =
                        parameters.copy(
                            subTitle = parameters.subTitle.copy(
                                position = parameters.subTitle.position.copy(
                                    x = event.value ?: parameters.subTitle.position.x
                                )
                            ),
                        )
                    createPreviewFile()
                }

                is MainEvent.ChangeSubTitleY -> {
                    parameters =
                        parameters.copy(
                            subTitle = parameters.subTitle.copy(
                                position = parameters.subTitle.position.copy(
                                    y = event.value ?: parameters.subTitle.position.y
                                )
                            ),
                        )
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
                    imageRepository.updateParameters(event.parameters)
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
        isExit = isExit,
    )
}
