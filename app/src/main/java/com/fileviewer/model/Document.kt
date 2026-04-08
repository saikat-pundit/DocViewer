package com.fileviewer.model

data class Document(
    val fileName: String,
    val filePath: String,
    val fileSize: Long,
    val fileType: FileType,
    val content: Any? = null
)

enum class FileType {
    PDF,
    TXT,
    CSV,
    HTML,
    DOCX,
    XLSX,
    UNSUPPORTED
}
