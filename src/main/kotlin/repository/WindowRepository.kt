package repository

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.set
import model.Window
import java.util.prefs.Preferences

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