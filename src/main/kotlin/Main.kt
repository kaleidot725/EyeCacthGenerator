import androidx.compose.foundation.background
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.application
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import jp.kaleidot725.eyegen.eyegen.generated.resources.Res
import jp.kaleidot725.eyegen.eyegen.generated.resources.icon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import model.Parameters
import model.Window
import model.Window.Companion.toWindow
import model.Window.Companion.toWindowState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.intui.standalone.theme.darkThemeDefinition
import org.jetbrains.jewel.intui.window.decoratedWindow
import org.jetbrains.jewel.intui.window.styling.dark
import org.jetbrains.jewel.ui.ComponentStyling
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.window.DecoratedWindow
import org.jetbrains.jewel.window.TitleBar
import org.jetbrains.jewel.window.newFullscreenControls
import org.jetbrains.jewel.window.styling.TitleBarStyle
import repository.ImageRepository
import repository.WindowRepository
import view.MainEvent
import view.MainProcessor
import view.MainScreen
import view.MainState
import java.io.File
import java.util.Date

val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
val event = MutableSharedFlow<MainEvent>()

// Window
val windowRepository = WindowRepository()
val window = windowRepository.get()

// Image
val imageRepository = ImageRepository()

val stateFlow: Flow<MainState> = scope.launchMolecule(mode = RecompositionMode.Immediate) {
    MainProcessor(
        event = event,
        windowRepository = windowRepository,
        imageRepository = imageRepository
    )
}

@OptIn(ExperimentalResourceApi::class)
fun main() = application {
    val density = LocalDensity.current
    val windowState by remember(window) { mutableStateOf(window.toWindowState(density)) }
    val state by stateFlow.collectAsState(
        MainState(
            parameters = Parameters(
                title = "Hello World",
                subTitle = "Hello World",
                width = 1920,
                height = 1080,
                startColor = Color.Red.value,
                endColor = Color.Blue.value,
            ),
            previewFile = File(""),
            previewUpdate = Date().time,
            isExit = false,
            window = Window()
        )
    )

    SideEffect {
        println(windowState.toString())
    }

    LaunchedEffect(state.isExit) {
        if (state.isExit) exitApplication()
    }

    IntUiTheme(
        theme = JewelTheme.darkThemeDefinition(),
        styling = ComponentStyling.decoratedWindow(titleBarStyle = TitleBarStyle.dark()),
    ) {
        DecoratedWindow(
            title = "EyeGen",
            state = windowState,
            icon = painterResource(Res.drawable.icon),
            onCloseRequest = {
                scope.launch { event.emit(MainEvent.Destroy(windowState.toWindow(density))) }
            },
        ) {
            TitleBar(
                style = TitleBarStyle.dark(),
                modifier = Modifier.newFullscreenControls(),
            ) {
                Text(text = "EyeGen", textAlign = TextAlign.Center)
            }
            MainScreen(
                state = state,
                onEvent = { scope.launch { event.emit(it) } },
                modifier = Modifier.background(JewelTheme.globalColors.paneBackground)
            )
        }
    }
}
