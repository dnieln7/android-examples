package xyz.dnieln7.media.common

enum class MediaType(val directory: String, val mimeType: String) {
    PICTURE(directory = "Camera", mimeType = "image/png"),
    SCREENSHOT(directory = "Screenshots", mimeType = "image/png"),
    VIDEO(directory = "Recordings", mimeType = "video/mp4");

    fun buildName(): String {
        val timestamp = System.currentTimeMillis()

        return when (this) {
            PICTURE -> "picture_${timestamp}.png"
            SCREENSHOT -> "screenshot_${timestamp}.png"
            VIDEO -> "recording_${timestamp}.mp4"
        }
    }
}