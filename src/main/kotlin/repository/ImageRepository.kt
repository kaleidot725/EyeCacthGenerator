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

                imageGraphic.drawString(parameters.title, 0, 100)
                imageGraphic.drawString(parameters.subTitle, 0, 200)
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

    private fun Color.toAwtColor() : java.awt.Color {
        return java.awt.Color(red, green, blue, alpha)
    }
}