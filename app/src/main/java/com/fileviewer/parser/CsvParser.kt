package com.fileviewer.parser

import android.content.Context
import com.opencsv.CSVReader
import com.fileviewer.model.TableData
import java.io.InputStreamReader

object CsvParser {
    
    fun parse(context: Context, inputStream: InputStream): TableData {
        val reader = CSVReader(InputStreamReader(inputStream))
        val allRows = reader.readAll()
        
        return if (allRows.isNotEmpty()) {
            val headers = allRows[0]
            val dataRows = allRows.drop(1)
            TableData(headers, dataRows)
        } else {
            TableData(emptyList(), emptyList())
        }
    }
}
