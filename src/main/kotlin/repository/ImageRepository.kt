package repository

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.params.Parameters
import util.OSContext
import java.awt.Font
import java.awt.GradientPaint
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.nio.file.Files
import javax.imageio.ImageIO

class ImageRepository {
    private val outputDirectory = File(OSContext.resolveOSContext().directory, "temp")
    private val outputFile = File(outputDirectory, "preview.png")

    suspend fun createPreview(parameters: Parameters): File =
        withContext(Dispatchers.IO) {
            try {
                Files.createDirectories(outputDirectory.toPath())
            } catch (e: Exception) {
                println(e)
            }

            try {
                Files.delete(outputFile.toPath())
            } catch (e: Exception) {
                println(e)
            }

            if (parameters.isValid) {
                try {
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
                            parameters.width?.toFloat() ?: 0f,
                            parameters.height?.toFloat() ?: 0f,
                            endColor.toAwtColor(),
                        )
                    imageGraphic.fillRect(0, 0, parameters.width ?: 0, parameters.height ?: 0)

                    imageGraphic.color = titleColor.toAwtColor()
                    val titleFont = Font(parameters.title.font.value, Font.PLAIN, parameters.title.size ?: 0)
                    val titleWidth = getWidth(titleFont, parameters.title.text)
                    val titleHeight = getHeight(titleFont)
                    val titleX = getCenterX(maxWidth = parameters.width ?: 0, textWidth = titleWidth)
                    val titleY = getCenterY(maxHeight = parameters.height ?: 0, textHeight = titleHeight)

                    imageGraphic.font = titleFont
                    imageGraphic.drawString(parameters.title.text, titleX, titleY)

                    imageGraphic.color = subTitleColor.toAwtColor()
                    val subTitleFont = Font(parameters.subTitle.font.value, Font.PLAIN, parameters.subTitle.size ?: 0)
                    val subTitleWidth = getWidth(subTitleFont, parameters.subTitle.text)
                    val subTitleHeight = getHeight(subTitleFont)
                    val subTitleX =
                        getCenterX(
                            maxWidth = parameters.width ?: 0,
                            textWidth = subTitleWidth,
                        )
                    val subTitleY =
                        getCenterY(
                            maxHeight = parameters.height ?: 0,
                            textHeight = subTitleHeight,
                        ) + titleHeight

                    imageGraphic.font = subTitleFont
                    imageGraphic.drawString(parameters.subTitle.text, subTitleX, subTitleY)

                    ImageIO.write(output, "PNG", outputFile)
                    outputFile
                } catch (e: IOException) {
                    outputFile
                }
            } else {
                File("")
            }
        }

    suspend fun createFileToDesktop(parameters: Parameters): Boolean = false

    private fun Color.toAwtColor(): java.awt.Color = java.awt.Color(red, green, blue, alpha)

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

    private fun getCenterX(
        maxWidth: Int,
        textWidth: Int,
    ): Int = maxWidth / 2 - textWidth / 2

    private fun getCenterY(
        maxHeight: Int,
        textHeight: Int,
    ): Int = maxHeight / 2 - textHeight / 2
}
