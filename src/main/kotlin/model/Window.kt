package model

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState

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