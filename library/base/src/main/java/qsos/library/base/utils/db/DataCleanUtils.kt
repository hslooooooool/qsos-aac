package qsos.library.base.utils.db

import android.content.Context
import android.os.Environment

import java.io.File
import java.math.BigDecimal

/**
 * @author : 华清松
 * @date : 2018/10/11 11:26
 * @description : APP 存储数据工具类，用于清除缓存
 */
object DataCleanUtils {
    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getTotalCacheSize(context: Context): String {
        var cacheSize = getFolderSize(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            cacheSize += getFolderSize(context.externalCacheDir)
        }
        return getFormatSize(cacheSize.toDouble())
    }

    /**
     * 清除所有缓存
     *
     * @param context
     */
    fun clearAllCache(context: Context): Boolean {
        var externalCacheDir = true
        val cacheDir = deleteDir(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            externalCacheDir = deleteDir(context.externalCacheDir)
        }
        return cacheDir or externalCacheDir
    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
        }
        return dir!!.delete()
    }

    // 获取文件大小
    // Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    // Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    private fun getFolderSize(file: File?): Long {
        if (file == null) {
            return 0L
        }
        return try {
            var size = 0L
            val fileList = file.listFiles()
            for (i in fileList.indices) {
                // 如果下面还有文件
                size += if (fileList[i].isDirectory) getFolderSize(fileList[i]) else fileList[i].length()
            }
            size
        } catch (e: Exception) {
            0L
        }
    }

    /**
     * 格式化缓存大小单位
     */
    private fun getFormatSize(size: Double): String {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
            return "0 K"
        }
        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 = BigDecimal(java.lang.Double.toString(kiloByte))
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " K"
        }
        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(java.lang.Double.toString(megaByte))
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " M"
        }
        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " GB"
        }
        val result4 = BigDecimal(teraBytes)
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " TB"
    }
}
