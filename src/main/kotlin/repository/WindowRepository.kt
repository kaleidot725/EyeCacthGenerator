package repository

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.set
import data.PreferenceKeys
import model.Window
import java.util.prefs.Preferences

class WindowRepository {
    private val settings: ObservableSettings = PreferencesSettings(Preferences.userRoot())

    fun update(state: Window) {
        settings[PreferenceKeys.WINDOW_WIDTH] = state.width
        settings[PreferenceKeys.WINDOW_HEIGHT] = state.height
        settings[PreferenceKeys.WINDOW_X] = state.x
        settings[PreferenceKeys.WINDOW_Y] = state.y
    }

    fun get(): Window {
        return Window(
            width = settings.getFloat(PreferenceKeys.WINDOW_WIDTH, 1200f),
            height = settings.getFloat(PreferenceKeys.WINDOW_HEIGHT, 800f),
            x = settings.getFloat(PreferenceKeys.WINDOW_X, 0f),
            y = settings.getFloat(PreferenceKeys.WINDOW_Y, 0f)
        )
    }
}