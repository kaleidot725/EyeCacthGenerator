package repository

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.Parameters
import util.OSContext
import java.awt.Font
import java.awt.GradientPaint
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.nio.file.Files
import javax.imageio.ImageIO


class ImageRepository {
    private val outputDirectory = File(OSContext.resolveOSContext().directory, "temp")
    private val outputFile = File(outputDirectory, "preview.png")

    suspend fun createPreview(parameters: Parameters): File {
        return withContext(Dispatchers.IO) {
            try {
                Files.createDirectories(outputDirectory.toPath())
            } catch (e: Exception) {
                println(e)
            }

            try {
                Files.delete(outputFile.toPath())
            } catch (e: Exception) {

            }

            try {
                val startColor = Color(parameters.startColor)
                val endColor = Color(parameters.endColor)
                val output = BufferedImage(
                    parameters.width,
                    parameters.height,
                    BufferedImage.TYPE_INT_ARGB
                )
                val imageGraphic = output.createGraphics()
                imageGraphic.paint = GradientPaint(
                    0f,
                    0f,
                    startColor.toAwtColor(),
                    parameters.width.toFloat(),
                    parameters.height.toFloat(),
                    endColor.toAwtColor()
                )
                imageGraphic.fillRect(0, 0, parameters.width, parameters.height)
                imageGraphic.color = java.awt.Color.black

                val titleFont = Font(parameters.titleFont.value, Font.PLAIN, 48)
                val titleWidth = getWidth(titleFont, parameters.title)
                val titleHeight = getHeight(titleFont)
                val titleX = getCenterX(maxWidth = parameters.width, textWidth = titleWidth)
                val titleY = getCenterY(maxHeight = parameters.height, textHeight = titleHeight)

                imageGraphic.font = titleFont
                imageGraphic.drawString(parameters.title, titleX, titleY)

                val subTitleFont = Font(parameters.subTitleFont.value, Font.PLAIN, 48)
                val subTitleWidth = getWidth(subTitleFont, parameters.subTitle)
                val subTitleHeight = getHeight(subTitleFont)
                val subTitleX = getCenterX(maxWidth = parameters.width, textWidth = subTitleWidth)
                val subTitleY = getCenterY(maxHeight = parameters.height, textHeight = subTitleHeight) + titleHeight

                imageGraphic.font = subTitleFont
                imageGraphic.drawString(parameters.subTitle, subTitleX, subTitleY)

                ImageIO.write(output, "PNG", outputFile)
                outputFile
            } catch (e: IOException) {
                outputFile
            }
        }
    }

    suspend fun createFileToDesktop(parameters: Parameters): Boolean {
        return false
    }

    private fun Color.toAwtColor(): java.awt.Color {
        return java.awt.Color(red, green, blue, alpha)
    }

    private fun getHeight(font: Font): Int {
        val output = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        val imageGraphic = output.createGraphics().apply { this.font = font }
        return imageGraphic.fontMetrics.height.apply { imageGraphic.dispose() }
    }

    private fun getWidth(font: Font, text: String): Int {
        val output = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        val imageGraphic = output.createGraphics().apply { this.font = font }
        return imageGraphic.fontMetrics.stringWidth(text).apply { imageGraphic.dispose() }
    }

    private fun getCenterX(maxWidth: Int, textWidth: Int): Int {
        return maxWidth / 2 - textWidth / 2
    }

    private fun getCenterY(maxHeight: Int, textHeight: Int): Int {
        return maxHeight / 2 - textHeight / 2
    }
}