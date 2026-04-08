package com.fileviewer.parser

import android.content.Context
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import com.fileviewer.model.TableData
import java.io.InputStream

object XlsxParser {
    
    fun parse(context: Context, inputStream: InputStream): List<TableData> {
        val workbook = XSSFWorkbook(inputStream)
        val sheets = mutableListOf<TableData>()
        
        for (i in 0 until workbook.numberOfSheets) {
            val sheet = workbook.getSheetAt(i)
            val headers = mutableListOf<String>()
            val rows = mutableListOf<List<String>>()
            
            val rowIterator = sheet.iterator()
            var isFirstRow = true
            
            while (rowIterator.hasNext()) {
                val row = rowIterator.next()
                val cells = mutableListOf<String>()
                
                for (cell in row) {
                    val value = when (cell.cellType) {
                        org.apache.poi.ss.usermodel.CellType.STRING -> cell.stringCellValue
                        org.apache.poi.ss.usermodel.CellType.NUMERIC -> cell.numericCellValue.toString()
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
            }
            
            sheets.add(TableData(headers, rows))
        }
        
        workbook.close()
        return sheets
    }
}
