package com.fileviewer.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.fileviewer.parser.XlsxParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileInputStream

class XlsxViewerFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val scrollView = ScrollView(requireContext())
        val textView = TextView(requireContext())
        textView.setPadding(50, 50, 50, 50)
        textView.textSize = 14f
        textView.typeface = android.graphics.Typeface.MONOSPACE
        scrollView.addView(textView)
        
        val filePath = arguments?.getString("file_path")
        filePath?.let {
            loadXlsx(it, textView)
        }
        
        return scrollView
    }
    
    private fun loadXlsx(path: String, textView: TextView) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val inputStream = FileInputStream(path)
                val sheets = XlsxParser.parse(requireContext(), inputStream)
                val stringBuilder = StringBuilder()
                
                for ((index, sheet) in sheets.withIndex()) {
                    stringBuilder.append("Sheet ${index + 1}:\n")
                    stringBuilder.append(sheet.headers.joinToString(" | ")).append("\n")
                    stringBuilder.append("-".repeat(50)).append("\n")
                    for (row in sheet.rows.take(100)) {
                        stringBuilder.append(row.joinToString(" | ")).append("\n")
                    }
                    if (sheet.rows.size > 100) {
                        stringBuilder.append("\n... and ${sheet.rows.size - 100} more rows\n")
                    }
                    stringBuilder.append("\n\n")
                }
                
                withContext(Dispatchers.Main) {
                    textView.text = stringBuilder.toString()
                }
            }
        }
    }
}
