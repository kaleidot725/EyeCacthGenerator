package repository

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.set
import java.util.prefs.Preferences

data class Window(
    val width: Float = 1200f,
    val height: Float = 800f,
    val x: Float = 0f,
    val y: Float = 0f,
) {
    companion object {
        fun WindowState.toWindow(density: Density) : Window {
            return Window(
                width = with(density) { this@toWindow.size.width.toPx() },
                height = with(density) { this@toWindow.size.height.toPx() },
                x = with(density) { this@toWindow.position.x.toPx() },
                y = with(density) { this@toWindow.position.y.toPx() },
            )
        }

        fun Window.toWindowState(density: Density) : WindowState {
            return WindowState(
                size = DpSize(
                    width = with(density) { this@toWindowState.width.toDp() },
                    height = with(density) { this@toWindowState.height.toDp() },
                ),
                position = WindowPosition.Absolute(
                    x = with(density) { this@toWindowState.x.toDp() },
                    y = with(density) { this@toWindowState.y.toDp() },
                )
            )
        }
    }
}

class WindowRepository {
    private val settings: ObservableSettings = PreferencesSettings(Preferences.userRoot())

    fun update(state: Window) {
        settings["width"] = state.width
        settings["height"] = state.height
        settings["x"] = state.x
        settings["y"] = state.y
    }

    fun get() : Window {
        return Window(
            width = settings.getFloat("width", 1200f),
            height = settings.getFloat("height", 800f),
            x = settings.getFloat("x", 0f),
            y = settings.getFloat("y", 0f)
        )
    }
}