package com.fileviewer.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.fileviewer.R
import com.fileviewer.databinding.FragmentPdfViewerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class PdfViewerFragment : Fragment() {
    
    private var _binding: FragmentPdfViewerBinding? = null
    private val binding get() = _binding!!
    private var filePath: String? = null
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPdfViewerBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filePath = arguments?.getString("file_path")
        filePath?.let { loadPdf(it) }
    }
    
    private fun loadPdf(path: String) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val file = File(path)
                if (file.exists()) {
                    withContext(Dispatchers.Main) {
                        binding.pdfViewer.fromFile(file).load()
                    }
                }
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
