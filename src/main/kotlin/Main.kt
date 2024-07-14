import androidx.compose.foundation.background
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import view.MainEvent
import view.MainProcessor
import view.MainScreen
import view.MainState

val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
val event = MutableSharedFlow<MainEvent>()
val stateFlow: Flow<MainState> = scope.launchMolecule(mode = RecompositionMode.Immediate) { MainProcessor(event) }

@OptIn(ExperimentalResourceApi::class)
fun main() = application {
    val state by stateFlow.collectAsState(
        MainState(
            title = "Hello World",
            subTitle = "Hello World",
            width = 1920,
            height = 1080,
            startColor = Color.Red,
            endColor = Color.Blue
        )
    )

    IntUiTheme(
        theme = JewelTheme.darkThemeDefinition(),
        styling = ComponentStyling.decoratedWindow(titleBarStyle = TitleBarStyle.dark()),
    ) {
        DecoratedWindow(
            title = "EyeGen",
            icon = painterResource(Res.drawable.icon),
            onCloseRequest = ::exitApplication,
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
