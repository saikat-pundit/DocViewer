package com.fileviewer.parser

import android.content.Context
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.InputStream

object DocxParser {
    
    fun parse(context: Context, inputStream: InputStream): String {
    return try {
        val document = XWPFDocument(inputStream)
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
        
        document.close()
        stringBuilder.toString()
    } catch (e: Exception) {
        // Log and return fallback message instead of crashing
        android.util.Log.e("DocxParser", "Parse error: ${e.message}", e)
        "Error loading document: ${e.localizedMessage ?: "Unknown error"}"
    } finally {
        try { inputStream.close() } catch (_: Exception) {}
    }
}
}
