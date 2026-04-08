package com.fileviewer.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Build

object MemoryOptimizer {
    
    fun getAvailableMemory(context: Context): Long {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.availMem
    }
    
    fun isLowMemoryDevice(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activityManager.isLowRamDevice
        } else {
            val memoryInfo = ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memoryInfo)
            memoryInfo.totalMem < 1024 * 1024 * 1024
        }
    }
    
    fun getOptimalChunkSize(context: Context): Int {
        return if (isLowMemoryDevice(context)) {
            1024 * 1024
        } else {
        5120 * 1024
        }
    }
}
