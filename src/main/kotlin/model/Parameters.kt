package model

import androidx.compose.ui.graphics.Color

data class Parameters(
    val title: String,
    val titleFont: Font,
    val titleSize: Int?,
    val subTitle: String,
    val subTitleFont: Font,
    val subTitleSize: Int?,
    val width: Int?,
    val height: Int?,
    val startColor: ULong,
    val endColor: ULong,
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
                title = "Hello World",
                titleFont = Font("Arial"),
                titleSize = 48,
                subTitle = "Hello World",
                subTitleFont = Font("Arial"),
                subTitleSize = 48,
                width = 1920,
                height = 1080,
                startColor = Color.Red.value,
                endColor = Color.Blue.value,
            )
    }
}
