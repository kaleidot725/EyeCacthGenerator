package util

sealed class OSContext(val directory: String = "") {
    data object Mac : OSContext(System.getProperty("user.home") + "/Library/Application Support/Eyegen/")

    data object Windows : OSContext(System.getProperty("user.home") + "/Eyegen/")

    companion object {
        fun resolveOSContext(): OSContext {
            return when (System.getProperty("os.name")) {
                "Mac OS X" -> Mac
                else -> Windows
            }
        }
    }
}