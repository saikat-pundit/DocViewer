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
import java.io.IOException

class XlsxViewerFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val scrollView = ScrollView(requireContext())
        val textView = TextView(requireContext()).apply {
            setPadding(50, 50, 50, 50)
            textSize = 14f
            typeface = android.graphics.Typeface.MONOSPACE
        }
        scrollView.addView(textView)
        
        val filePath = arguments?.getString("file_path")
        filePath?.let {
            loadXlsx(it, textView)
        }
        
        return scrollView
    }
    
    private fun loadXlsx(path: String, textView: TextView) {
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    // ✅ Auto-close InputStream to prevent leaks
                    FileInputStream(path).use { inputStream ->
                        val sheets = XlsxParser.parse(inputStream)
                        
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
                        
                        // ✅ Check if fragment is still attached before UI update
                        if (isAdded && !requireActivity().isFinishing) {
                            withContext(Dispatchers.Main) {
                                textView.text = stringBuilder.toString()
                            }
                        }
                    }
                }
            } catch (e: NoClassDefFoundError) {
                // ✅ Handle missing POI classes (ProGuard issue)
                withContext(Dispatchers.Main) {
                    if (isAdded) {
                        textView.text = "Error: Missing library components.\n\nPlease reinstall the app or contact support.\n\n[${e.localizedMessage ?: e.message}]"
                        textView.setTextColor(android.graphics.Color.RED)
                    }
                }
                android.util.Log.e("XlsxViewer", "Missing class: ${e.message}", e)
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    if (isAdded) {
                        textView.text = "Error reading file:\n${e.localizedMessage}"
                        textView.setTextColor(android.graphics.Color.RED)
                    }
                }
                android.util.Log.e("XlsxViewer", "IO error: ${e.message}", e)
            } catch (t: Throwable) { // ✅ FIXED: Now catches fatal system errors too
                withContext(Dispatchers.Main) {
                    if (isAdded) {
                        textView.text = "Failed to load spreadsheet:\n${t.localizedMessage ?: t.javaClass.simpleName}\n\nTry a smaller file."
                        textView.setTextColor(android.graphics.Color.RED)
                    }
                }
                android.util.Log.e("XlsxViewer", "Parse error: ${t.message}", t)
            }
        }
    }
}
