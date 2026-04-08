package com.fileviewer.parser

import android.content.Context
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import com.fileviewer.model.TableData
import java.io.InputStream

object XlsxParser {
    
    fun parse(context: Context, inputStream: InputStream): List<TableData> {
    return try {
        val workbook = XSSFWorkbook(inputStream)
        val sheets = mutableListOf<TableData>()
        
        for (i in 0 until workbook.numberOfSheets) {
            val sheet = workbook.getSheetAt(i)
            val headers = mutableListOf<String>()
            val rows = mutableListOf<List<String>>()
            
            val rowIterator = sheet.iterator()
            var isFirstRow = true
            var rowCount = 0
            val MAX_ROWS = 500 // Prevent OOM on large files
            
            while (rowIterator.hasNext() && rowCount < MAX_ROWS + 1) {
                val row = rowIterator.next()
                val cells = mutableListOf<String>()
                
                for (cell in row) {
                    val value = when (cell.cellType) {
                        org.apache.poi.ss.usermodel.CellType.STRING -> cell.stringCellValue
                        org.apache.poi.ss.usermodel.CellType.NUMERIC -> {
                            if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                cell.dateCellValue.toString()
                            } else {
                                cell.numericCellValue.toString()
                            }
                        }
                        org.apache.poi.ss.usermodel.CellType.BOOLEAN -> cell.booleanCellValue.toString()
                        org.apache.poi.ss.usermodel.CellType.FORMULA -> cell.cellFormula
                        else -> ""
                    }
                    cells.add(value)
                }
                
                if (isFirstRow) {
                    headers.addAll(cells)
                    isFirstRow = false
                } else {
                    rows.add(cells)
                }
                rowCount++
            }
            
            sheets.add(TableData(headers, rows))
        }
        
        workbook.close()
        sheets
    } catch (e: Exception) {
        android.util.Log.e("XlsxParser", "Parse error: ${e.message}", e)
        listOf(TableData(listOf("Error"), listOf(listOf(e.localizedMessage ?: "Failed to parse file"))))
    } finally {
        try { inputStream.close() } catch (_: Exception) {}
    }
}
}
