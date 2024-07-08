import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.application
import jp.kaleidot725.eyegen.eyegen.generated.resources.Res
import jp.kaleidot725.eyegen.eyegen.generated.resources.icon
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

@OptIn(ExperimentalResourceApi::class)
fun main() = application {
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
                modifier = Modifier.background(JewelTheme.globalColors.paneBackground)
            )
        }
    }
}
