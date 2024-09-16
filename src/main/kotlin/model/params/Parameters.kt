package model.params

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable
import model.Font

@Serializable
data class Parameters(
    val title: TitleParameter,
    val subTitle: TitleParameter,
    val width: Int?,
    val height: Int?,
    val startColor: ULong?,
    val endColor: ULong?,
) {
    val isValid: Boolean
        get() {
            return width != null &&
                    width != 0 &&
                    height != null &&
                    height != 0
        }

    companion object {
        val initValue =
            Parameters(
                title =
                TitleParameter(
                    "Hello World",
                    Font("Arial"),
                    48,
                    color = Color.White.value,
                    position = PositionParameter(0.5f, 0.5f),
                ),
                subTitle =
                TitleParameter(
                    "Hello World",
                    Font("Arial"),
                    48,
                    color = Color.White.value,
                    position = PositionParameter(0.5f, 0.5f),
                ),
                width = 1920,
                height = 1080,
                startColor = Color.Red.value,
                endColor = Color.Blue.value,
            )
    }
}
