package repository

import model.Font
import java.awt.GraphicsEnvironment

class FontRepository {
    fun get(): List<Font> {
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val fonts = ge.availableFontFamilyNames
        return fonts.map { Font(it) }
    }
}