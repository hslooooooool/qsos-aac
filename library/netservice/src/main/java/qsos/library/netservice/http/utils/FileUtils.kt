package qsos.library.netservice.http.utils

import android.os.Environment

import java.io.File

object FileUtils {

    /**
     * 获得下载保存默认地址
     */
    val defaultDownLoadPath: String
        get() = if (checkSDStatus()) Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + File.separator else ""

    /**
     * 从url中，获得默认文件名
     */
    fun getDefaultDownLoadFileName(url: String?): String {
        if (url == null || url.isEmpty()) return ""
        val nameStart = url.lastIndexOf('/') + 1
        return url.substring(nameStart)
    }

    /**
     * 检查sd卡状态
     */
    private fun checkSDStatus(): Boolean {
        val sdStatus = Environment.getExternalStorageState()
        return sdStatus == Environment.MEDIA_MOUNTED
    }
}
