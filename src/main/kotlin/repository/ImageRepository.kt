package repository

import androidx.compose.ui.graphics.Color
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import data.PreferenceKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.params.Parameters
import model.params.Position
import model.params.TitleParameter
import util.OSContext
import java.awt.Font
import java.awt.GradientPaint
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.Date
import java.util.prefs.Preferences
import javax.imageio.ImageIO

class ImageRepository {
    private val settings: ObservableSettings = PreferencesSettings(Preferences.userRoot())
    val localParameters get() = getParameters()

    suspend fun createPreview(parameters: Parameters): File {
        return withContext(Dispatchers.IO) {
            val previewDirectory = File(OSContext.resolveOSContext().directory, "temp")
            val previewFile = File(previewDirectory, "preview.png")
            createImage(
                directoryPath = previewDirectory.toPath(),
                filePath = previewFile.toPath(),
                parameters = parameters
            ) ?: File("")
        }
    }

    suspend fun createFileToDesktop(parameters: Parameters): Boolean {
        return withContext(Dispatchers.IO) {
            val desktopDirectory = File(System.getProperty("user.home"), "Desktop")
            val desktopFile = File(desktopDirectory, "output_${Date().time}.png")
            val outputFile = createImage(
                directoryPath = desktopDirectory.toPath(),
                filePath = desktopFile.toPath(),
                parameters = parameters
            )
            outputFile != null
        }
    }

    suspend fun createImage(directoryPath: Path, filePath: Path, parameters: Parameters): File? =
        withContext(Dispatchers.IO) {
            try {
                Files.createDirectories(directoryPath)
            } catch (e: Exception) {
                println(e)
            }

            try {
                Files.delete(filePath)
            } catch (e: Exception) {
                println(e)
            }

            if (parameters.isValid) {
                try {
                    println(parameters)

                    val imageWidth = parameters.width?.toFloat() ?: 0f
                    val imageHeight = parameters.height?.toFloat() ?: 0f

                    val titleColor = Color(parameters.title.color ?: Color.Transparent.value)
                    val subTitleColor = Color(parameters.subTitle.color ?: Color.Transparent.value)
                    val startColor = Color(parameters.startColor ?: Color.Transparent.value)
                    val endColor = Color(parameters.endColor ?: Color.Transparent.value)
                    val output =
                        BufferedImage(
                            parameters.width ?: 0,
                            parameters.height ?: 0,
                            BufferedImage.TYPE_INT_ARGB,
                        )

                    val imageGraphic = output.createGraphics()
                    imageGraphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                    imageGraphic.paint =
                        GradientPaint(
                            0f,
                            0f,
                            startColor.toAwtColor(),
                            imageWidth,
                            imageHeight,
                            endColor.toAwtColor(),
                        )
                    imageGraphic.fillRect(0, 0, parameters.width ?: 0, parameters.height ?: 0)

                    imageGraphic.color = titleColor.toAwtColor()

                    val titleFont = Font(parameters.title.font.value, Font.PLAIN, parameters.title.size ?: 0)
                    val titleWidth = getWidth(titleFont, parameters.title.text)
                    val titleHeight = getHeight(titleFont)
                    val titleX = imageWidth * parameters.title.position.x - titleWidth / 2
                    val titleY = imageHeight * parameters.title.position.y + titleHeight / 2

                    imageGraphic.font = titleFont
                    imageGraphic.drawString(parameters.title.text, titleX, titleY)

                    imageGraphic.color = subTitleColor.toAwtColor()
                    val subTitleFont = Font(parameters.subTitle.font.value, Font.PLAIN, parameters.subTitle.size ?: 0)
                    val subTitleWidth = getWidth(subTitleFont, parameters.subTitle.text)
                    val subTitleHeight = getHeight(subTitleFont)
                    val subTitleX = imageWidth * parameters.subTitle.position.x - subTitleWidth / 2
                    val subTitleY = imageHeight * parameters.subTitle.position.y + subTitleHeight / 2

                    imageGraphic.font = subTitleFont
                    imageGraphic.drawString(parameters.subTitle.text, subTitleX, subTitleY)

                    ImageIO.write(output, "PNG", filePath.toFile())
                    updateParameters(parameters)
                    filePath.toFile()
                } catch (_: IOException) {
                    null
                }
            } else {
                null
            }
        }

    private fun Color.toAwtColor(): java.awt.Color = java.awt.Color(red, green, blue, alpha)

    private fun updateParameters(parameters: Parameters) {
        settings[PreferenceKeys.IMAGE_TITLE_TEXT] = parameters.title.text
        settings[PreferenceKeys.IMAGE_TITLE_FONT] = parameters.title.font.value
        settings[PreferenceKeys.IMAGE_TITLE_SIZE] = parameters.title.size
        settings[PreferenceKeys.IMAGE_TITLE_COLOR] = parameters.title.color
        settings[PreferenceKeys.IMAGE_TITLE_POSITION_X] = parameters.title.position.x
        settings[PreferenceKeys.IMAGE_TITLE_POSITION_Y] = parameters.title.position.y

        settings[PreferenceKeys.IMAGE_SUBTITLE_TEXT] = parameters.subTitle.text
        settings[PreferenceKeys.IMAGE_SUBTITLE_FONT] = parameters.subTitle.font.value
        settings[PreferenceKeys.IMAGE_SUBTITLE_SIZE] = parameters.subTitle.size
        settings[PreferenceKeys.IMAGE_SUBTITLE_COLOR] = parameters.subTitle.color
        settings[PreferenceKeys.IMAGE_SUBTITLE_POSITION_X] = parameters.subTitle.position.x
        settings[PreferenceKeys.IMAGE_SUBTITLE_POSITION_Y] = parameters.subTitle.position.y

        settings[PreferenceKeys.IMAGE_WIDTH] = parameters.width
        settings[PreferenceKeys.IMAGE_HEIGHT] = parameters.height
        settings[PreferenceKeys.IMAGE_START_COLOR] = parameters.startColor
        settings[PreferenceKeys.IMAGE_END_COLOR] = parameters.endColor
    }

    private fun getParameters(): Parameters {
        return Parameters(
            title = TitleParameter(
                text = settings.getString(PreferenceKeys.IMAGE_TITLE_TEXT, ""),
                font = model.Font(settings.getString(PreferenceKeys.IMAGE_TITLE_FONT, "")),
                size = settings.getInt(PreferenceKeys.IMAGE_TITLE_SIZE, 20),
                color = settings[PreferenceKeys.IMAGE_TITLE_COLOR, 0L].toULong(),
                position = Position(
                    x = settings[PreferenceKeys.IMAGE_TITLE_POSITION_X, 0f],
                    y = settings[PreferenceKeys.IMAGE_TITLE_POSITION_Y, 0f],
                )
            ),
            subTitle = TitleParameter(
                text = settings.getString(PreferenceKeys.IMAGE_SUBTITLE_TEXT, ""),
                font = model.Font(settings.getString(PreferenceKeys.IMAGE_SUBTITLE_FONT, "")),
                size = settings.getInt(PreferenceKeys.IMAGE_SUBTITLE_SIZE, 20),
                color = settings[PreferenceKeys.IMAGE_SUBTITLE_COLOR, 0L].toULong(),
                position = Position(
                    x = settings[PreferenceKeys.IMAGE_SUBTITLE_POSITION_X, 0f],
                    y = settings[PreferenceKeys.IMAGE_SUBTITLE_POSITION_Y, 0f],
                )
            ),
            width = settings[PreferenceKeys.IMAGE_WIDTH, 1200],
            height = settings[PreferenceKeys.IMAGE_HEIGHT, 800],
            startColor = settings[PreferenceKeys.IMAGE_START_COLOR, 0L].toULong(),
            endColor = settings[PreferenceKeys.IMAGE_END_COLOR, 0L].toULong(),
        )
    }

    private fun getHeight(font: Font): Int {
        val output = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        val imageGraphic = output.createGraphics().apply { this.font = font }
        return imageGraphic.fontMetrics.height.apply { imageGraphic.dispose() }
    }

    private fun getWidth(
        font: Font,
        text: String,
    ): Int {
        val output = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        val imageGraphic = output.createGraphics().apply { this.font = font }
        return imageGraphic.fontMetrics.stringWidth(text).apply { imageGraphic.dispose() }
    }
}
