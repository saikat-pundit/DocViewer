package com.fileviewer.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.fileviewer.R
import com.fileviewer.databinding.FragmentTextViewerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class TextViewerFragment : Fragment() {
    
    private var _binding: FragmentTextViewerBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextViewerBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filePath = arguments?.getString("file_path")
        filePath?.let { loadText(it) }
    }
    
    private fun loadText(path: String) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val file = File(path)
                val content = file.readText()
                withContext(Dispatchers.Main) {
                    binding.textContent.text = content
                }
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
