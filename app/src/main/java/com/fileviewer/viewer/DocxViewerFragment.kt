package com.fileviewer.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.fileviewer.parser.DocxParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileInputStream

class DocxViewerFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val scrollView = ScrollView(requireContext())
        val textView = TextView(requireContext())
        textView.setPadding(50, 50, 50, 50)
        textView.textSize = 16f
        scrollView.addView(textView)
        
        val filePath = arguments?.getString("file_path")
        filePath?.let {
            loadDocx(it, textView)
        }
        
        return scrollView
    }
    
    private fun loadDocx(path: String, textView: TextView) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    // ✅ 1. Use .use {} to auto-close the stream and prevent memory leaks
                    FileInputStream(path).use { inputStream ->
                        val content = DocxParser.parse(inputStream)
                        
                        // ✅ 2. Check if fragment is still attached before updating UI
                        if (isAdded && !requireActivity().isFinishing) {
                            withContext(Dispatchers.Main) {
                                textView.text = content
                            }
                        }
                    }
                } catch (t: Throwable) { 
                    // ✅ Catching Throwable perfectly handles fatal XML errors
                    withContext(Dispatchers.Main) {
                        // ✅ Check if fragment is attached before showing error message
                        if (isAdded) {
                            textView.text = "Failed to load file:\n${t.localizedMessage ?: t.javaClass.simpleName}\n\nPlease try a smaller file."
                            textView.setTextColor(android.graphics.Color.RED)
                        }
                    }
                    android.util.Log.e("ViewerFragment", "Load error: ${t.message}", t)
                }
            }
        }
    }
}
