package com.fileviewer.utils

import android.webkit.MimeTypeMap
import com.fileviewer.model.FileType
import java.io.File

object FileTypeDetector {
    
    fun detect(file: File): FileType {
        val extension = file.extension.lowercase()
        
        return when (extension) {
            "pdf" -> FileType.PDF
            "txt" -> FileType.TXT
            "csv" -> FileType.CSV
            "html", "htm" -> FileType.HTML
            "docx", "doc" -> FileType.DOCX
            "xlsx", "xls" -> FileType.XLSX
            else -> {
                val mime = getMimeType(file.absolutePath)
                when {
                    mime == "application/pdf" -> FileType.PDF
                    mime == "text/html" -> FileType.HTML
                    mime == "text/csv" -> FileType.CSV
                    else -> FileType.UNSUPPORTED
                }
            }
        }
    }
    
    private fun getMimeType(url: String): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }
}
