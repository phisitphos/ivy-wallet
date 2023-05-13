package ivy.core.logic.data

import ivy.core.logic.data.primitives.NotBlankTrimmedString

enum class MimeType(val value: String) {
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    PDF("application/pdf"),
    UNKNOWN("application/octet-stream")
    // Add more mime types as needed
}

sealed interface AttachmentLocation {
    val value: String
}

data class FilePath(override val value: String) : AttachmentLocation
data class URL(override val value: String) : AttachmentLocation

data class AttachmentRef(
    val filename: NotBlankTrimmedString,
    val mimeType: MimeType,
    val location: AttachmentLocation
)
