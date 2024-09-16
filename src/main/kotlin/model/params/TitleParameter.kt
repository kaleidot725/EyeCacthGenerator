package model.params

import kotlinx.serialization.Serializable
import model.Font

@Serializable
data class TitleParameter(
    val text: String,
    val font: Font,
    val size: Int?,
    val color: ULong?,
    val position: PositionParameter,
)
