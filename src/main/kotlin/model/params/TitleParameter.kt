package model.params

import model.Font

data class TitleParameter(
    val text: String,
    val font: Font,
    val size: Int?,
    val color: ULong?,
)
