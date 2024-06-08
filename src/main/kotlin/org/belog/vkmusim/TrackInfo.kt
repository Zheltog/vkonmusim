package org.belog.vkmusim

data class TrackInfo(
    val artist: String? = null,
    val title: String? = null,
    val raw: String
) {
    fun toFormatted(): String {
        return if (artist == null || title == null) {
            raw.removeSuffix(".mp3").replace("_", " ")
        } else {
            "$artist - $title"
        }
    }
}