package com.fileviewer.parser

import android.content.Context
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.InputStream

object DocxParser {
    
    fun parse(context: Context, inputStream: InputStream): String {
        val document = XWPFDocument(inputStream)
        val stringBuilder = StringBuilder()
        
        val paragraphs = document.paragraphs
        for (paragraph in paragraphs) {
            stringBuilder.append(paragraph.text).append("\n\n")
        }
        
        val tables = document.tables
        for (table in tables) {
            for (row in table.rows) {
                for (cell in row.tableCells) {
                    stringBuilder.append(cell.text).append("\t")
                }
                stringBuilder.append("\n")
            }
            stringBuilder.append("\n")
        }
        
        document.close()
        return stringBuilder.toString()
    }
}
