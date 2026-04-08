package com.fileviewer.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fileviewer.databinding.FragmentPdfViewerBinding
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

class PdfViewerFragment : Fragment() {
    
    private var _binding: FragmentPdfViewerBinding? = null
    private val binding get() = _binding!!
    
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
        val filePath = arguments?.getString("file_path")
        filePath?.let { loadPdf(it) }
    }
    
    private fun loadPdf(path: String) {
        val file = File(path)
        if (file.exists()) {
            binding.pdfViewer.fromFile(file)
                .enableSwipe(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .load()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
