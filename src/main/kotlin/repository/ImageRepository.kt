package repository

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.Parameters
import util.OSContext
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.nio.file.Files
import javax.imageio.ImageIO

class ImageRepository {
    private val outputDirectory = File(OSContext.resolveOSContext().directory, "temp")
    private val outputFile = File(outputDirectory, "preview.png")

    suspend fun createPreview(parameters: Parameters) : File {
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
                val output = BufferedImage(
                    parameters.width,
                    parameters.height,
                    BufferedImage.TYPE_INT_ARGB
                )

                val color = Color(parameters.startColor)
                val awtColor = java.awt.Color(color.red, color.green, color.blue, color.alpha)
                output.graphics.color = awtColor
                output.graphics.fillRect(0, 0, parameters.width, parameters.height)
                ImageIO.write(output, "PNG", outputFile)
                outputFile
            } catch (e: IOException) {
                outputFile
            }
        }
    }

    suspend fun createFileToDesktop(parameters: Parameters) : Boolean {
        return false
    }
}