package com.fileviewer.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fileviewer.R
import com.fileviewer.databinding.FragmentHtmlViewerBinding
import java.io.File

class HtmlViewerFragment : Fragment() {
    
    private var _binding: FragmentHtmlViewerBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHtmlViewerBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filePath = arguments?.getString("file_path")
        filePath?.let { loadHtml(it) }
    }
    
    private fun loadHtml(path: String) {
        val file = File(path)
        if (file.exists()) {
            val htmlContent = file.readText()
            binding.webView.loadDataWithBaseURL(
                file.parentFile?.absolutePath,
                htmlContent,
                "text/html",
                "UTF-8",
                null
            )
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        binding.webView.destroy()
        _binding = null
    }
}
