package com.fileviewer.parser

import android.content.Context
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.InputStream

object DocxParser {
    fun parse(inputStream: InputStream): String {
    return try {
        // ✅ Use .use {} to auto-close document (and underlying stream) in ALL cases
        XWPFDocument(inputStream).use { document ->
            val stringBuilder = StringBuilder()
            
            // Extract paragraphs
            for (paragraph in document.paragraphs) {
                stringBuilder.append(paragraph.text).append("\n\n")
            }
            
            // Extract tables
            for (table in document.tables) {
                for (row in table.rows) {
                    for (cell in row.tableCells) {
                        stringBuilder.append(cell.text).append("\t")
                    }
                    stringBuilder.append("\n")
                }
                stringBuilder.append("\n")
            }
            
            stringBuilder.toString()  // ✅ Returned from inside .use {} block
        }
    } catch (e: Exception) {
        android.util.Log.e("DocxParser", "Parse error: ${e.message}", e)
        "Error loading document: ${e.localizedMessage ?: "Unknown error"}"
    }
    // ✅ No finally block needed - .use {} handles cleanup automatically
}
}
