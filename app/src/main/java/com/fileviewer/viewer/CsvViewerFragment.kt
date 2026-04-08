package com.fileviewer.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fileviewer.R
import com.fileviewer.databinding.FragmentCsvViewerBinding
import com.fileviewer.parser.CsvParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileInputStream

class CsvViewerFragment : Fragment() {
    
    private var _binding: FragmentCsvViewerBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCsvViewerBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filePath = arguments?.getString("file_path")
        filePath?.let { loadCsv(it) }
    }
    
    private fun loadCsv(path: String) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val inputStream = FileInputStream(path)
                val tableData = CsvParser.parse(requireContext(), inputStream)
                withContext(Dispatchers.Main) {
                    setupRecyclerView(tableData)
                }
            }
        }
    }
    
    private fun setupRecyclerView(tableData: com.fileviewer.model.TableData) {
        binding.csvTable.layoutManager = LinearLayoutManager(context)
        // Simple adapter would be implemented here
        // For brevity, showing basic text display
        val textContent = StringBuilder()
        textContent.append(tableData.headers.joinToString(" | ")).append("\n")
        textContent.append("-".repeat(50)).append("\n")
        for (row in tableData.rows) {
            textContent.append(row.joinToString(" | ")).append("\n")
        }
        
        val textView = android.widget.TextView(context)
        textView.text = textContent.toString()
        textView.setPadding(50, 50, 50, 50)
        textView.textSize = 14f
        textView.typeface = android.graphics.Typeface.MONOSPACE
        
        // Replace recyclerview with textview for simplicity
        binding.csvTable.visibility = android.view.View.GONE
        (binding.root as android.widget.LinearLayout).addView(textView)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
