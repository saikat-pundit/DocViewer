package com.fileviewer

import android.provider.OpenableColumns
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.fileviewer.databinding.ActivityMainBinding
import com.fileviewer.filepicker.FilePickerActivity
import com.fileviewer.model.FileType
import com.fileviewer.utils.FileTypeDetector
import com.fileviewer.utils.PermissionHelper
import com.fileviewer.viewer.*
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private var currentFilePath: String? = null
    
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            openFilePicker()
        } else {
            Toast.makeText(this, "Permission required to read files", Toast.LENGTH_SHORT).show()
        }
    }
    
    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            val uri = intent?.data
            uri?.let { handleSelectedFile(it) }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        
        binding.fabOpen.setOnClickListener {
            checkPermissionAndOpenPicker()
        }
        
        intent?.data?.let { uri ->
            handleSelectedFile(uri)
        }
    }
    
    private fun checkPermissionAndOpenPicker() {
        if (PermissionHelper.hasStoragePermission(this)) {
            openFilePicker()
        } else {
            permissionLauncher.launch(PermissionHelper.getRequiredPermissions())
        }
    }
    
    private fun openFilePicker() {
        val intent = Intent(this, FilePickerActivity::class.java)
        filePickerLauncher.launch(intent)
    }
    
    private fun handleSelectedFile(uri: Uri) {
        try {
            val filePath = copyUriToTempFile(uri)
            if (filePath != null) {
                currentFilePath = filePath
                val file = File(filePath)
                val fileType = FileTypeDetector.detect(file)
                displayFile(filePath, fileType)
            } else {
                Toast.makeText(this, "Failed to open file", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun copyUriToTempFile(uri: Uri): String? {
        return try {
            var originalFileName = "unknown_file"
            contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1 && cursor.moveToFirst()) {
                    originalFileName = cursor.getString(nameIndex)
                }
            }
            val tempFile = File(cacheDir, "temp_${System.currentTimeMillis()}_$originalFileName")
            
            val inputStream = contentResolver.openInputStream(uri)
            FileOutputStream(tempFile).use { outputStream ->
                inputStream?.copyTo(outputStream)
            }
            tempFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    private fun displayFile(filePath: String, fileType: FileType) {
        val fragment: Fragment = when (fileType) {
            FileType.PDF -> PdfViewerFragment()
            FileType.TXT -> TextViewerFragment()
            FileType.CSV -> CsvViewerFragment()
            FileType.HTML -> HtmlViewerFragment()
            FileType.DOCX -> DocxViewerFragment()
            FileType.XLSX -> XlsxViewerFragment()
            FileType.UNSUPPORTED -> {
                Toast.makeText(this, "Unsupported file type", Toast.LENGTH_SHORT).show()
                return
            }
        }
        
        val bundle = Bundle().apply {
            putString("file_path", filePath)
        }
        fragment.arguments = bundle
        
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
