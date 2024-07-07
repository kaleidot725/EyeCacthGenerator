import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.intellij.lang.annotations.Language
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

fun main() = application {
    IntUiTheme(
        theme = JewelTheme.darkThemeDefinition(),
        styling = ComponentStyling.decoratedWindow(titleBarStyle = TitleBarStyle.dark()),
    ) {
        DecoratedWindow(
            title = "EyeGen",
            icon = painterResource("icon.png"),
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
