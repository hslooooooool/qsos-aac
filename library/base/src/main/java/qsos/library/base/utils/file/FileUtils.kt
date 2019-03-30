package qsos.library.base.utils.file

import android.annotation.SuppressLint

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import qsos.library.base.R
import qsos.library.base.callback.OnTListener
import qsos.library.base.utils.ToastUtils
import qsos.library.base.utils.image.ImageLoaderUtils
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.*
import java.text.DecimalFormat

/**
 * @author : 华清松
 * @date : 2018/10/24 21:03
 * @description : 文件工具类
 */
object FileUtils {
    /**上传图片大小限制*/
    const val M10 = 10 * 1048576L
    /**上传文件大小限制*/
    const val M30 = 30 * 1048576L

    private var SDPATH = Environment.getExternalStorageDirectory().path
    private val ROOT_PATH = "$SDPATH/km_cache"
    private val VIDEO_PATH = "$ROOT_PATH/download"
    private val MIME_MapTable = arrayOf(arrayOf(".3gp", "video/3gpp"), arrayOf(".apk", "application/vnd.android.package-archive"), arrayOf(".asf", "video/x-ms-asf"), arrayOf(".avi", "video/x-msvideo"), arrayOf(".bin", "application/octet-stream"), arrayOf(".bmp", "image/bmp"), arrayOf(".c", "text/plain"), arrayOf(".class", "application/octet-stream"), arrayOf(".conf", "text/plain"), arrayOf(".cpp", "text/plain"), arrayOf(".doc", "application/msword"), arrayOf(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"), arrayOf(".xls", "application/vnd.ms-excel"), arrayOf(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), arrayOf(".exe", "application/octet-stream"), arrayOf(".gif", "image/gif"), arrayOf(".gtar", "application/x-gtar"), arrayOf(".gz", "application/x-gzip"), arrayOf(".h", "text/plain"), arrayOf(".htm", "text/html"), arrayOf(".html", "text/html"), arrayOf(".jar", "application/java-archive"), arrayOf(".java", "text/plain"), arrayOf(".jpeg", "image/jpeg"), arrayOf(".jpg", "image/jpeg"), arrayOf(".js", "application/x-javascript"), arrayOf(".log", "text/plain"), arrayOf(".m3u", "audio/x-mpegurl"), arrayOf(".m4a", "audio/mp4a-latm"), arrayOf(".m4b", "audio/mp4a-latm"), arrayOf(".m4p", "audio/mp4a-latm"), arrayOf(".m4u", "video/vnd.mpegurl"), arrayOf(".m4v", "video/x-m4v"), arrayOf(".mov", "video/quicktime"), arrayOf(".mp2", "audio/x-mpeg"), arrayOf(".mp3", "audio/x-mpeg"), arrayOf(".mp4", "video/mp4"), arrayOf(".mpc", "application/vnd.mpohun.certificate"), arrayOf(".mpe", "video/mpeg"), arrayOf(".mpeg", "video/mpeg"), arrayOf(".mpg", "video/mpeg"), arrayOf(".mpg4", "video/mp4"), arrayOf(".mpga", "audio/mpeg"), arrayOf(".msg", "application/vnd.ms-outlook"), arrayOf(".ogg", "audio/ogg"), arrayOf(".pdf", "application/pdf"), arrayOf(".png", "image/png"), arrayOf(".pps", "application/vnd.ms-powerpoint"), arrayOf(".ppt", "application/vnd.ms-powerpoint"), arrayOf(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"), arrayOf(".prop", "text/plain"), arrayOf(".rc", "text/plain"), arrayOf(".rmvb", "audio/x-pn-realaudio"), arrayOf(".rtf", "application/rtf"), arrayOf(".sh", "text/plain"), arrayOf(".tar", "application/x-tar"), arrayOf(".tgz", "application/x-compressed"), arrayOf(".txt", "text/plain"), arrayOf(".wav", "audio/x-wav"), arrayOf(".wma", "audio/x-ms-wma"), arrayOf(".wmv", "audio/x-ms-wmv"), arrayOf(".wps", "application/vnd.ms-works"), arrayOf(".xml", "text/plain"), arrayOf(".z", "application/x-compress"), arrayOf(".zip", "application/x-zip-compressed"), arrayOf("", "*/*"))
    val DOWNLOAD_PATH = "$ROOT_PATH/download"

    enum class TYPE(key: String) {
        PHOTO("拍照上传"),
        IMAGE("相册选择上传"),
        VIDEO("视频选择上传"),
        FILE("文件选择上传");

        val title = key
    }

    init {
        createFileHolder(DOWNLOAD_PATH)
        createFileHolder(VIDEO_PATH)
    }

    private fun createFileHolder(fileHolder: String) {
        val file = File(fileHolder)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    /**根据文件后缀名获得对应的 MIME 类型*/
    private fun getMIMEType(file: File): String {
        var type = "*/*"
        val fName = file.name
        // 获取后缀名前的分隔符"."在fName中的位置。
        val dotIndex = fName.lastIndexOf(".")
        if (dotIndex < 0) {
            return type
        }
        /* 获取文件的后缀名 */
        val end = fName.substring(dotIndex, fName.length).toLowerCase()
        if (end === "") return type
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (i in 0 until MIME_MapTable.size) {
            if (end == MIME_MapTable[i][0])
                type = MIME_MapTable[i][1]
        }
        return type
    }

    /**调用本地应用打开文件*/
    fun openFile(activity: Activity, file: File) {
        try {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            // 设置intent的Action属性
            intent.action = Intent.ACTION_VIEW
            // 获取文件file的MIME类型
            val type = getMIMEType(file)
            // 设置intent的data和Type属性。android 7.0以上crash,改用provider
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val fileUri = FileProvider.getUriForFile(activity, activity.packageName + ".provider", file)
                intent.setDataAndType(fileUri, type)
                grantUriPermission(activity, fileUri, intent)
            } else {
                intent.setDataAndType(Uri.fromFile(file), type)
            }
            // 跳转
            activity.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun grantUriPermission(context: Context, fileUri: Uri, intent: Intent) {
        val resInfoList = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        resInfoList.forEach {
            val packageName = it.activityInfo.packageName
            context.grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    fun getFile(context: Context, contentUri: Uri): File? {
        return try {
            File(getRealPathFromUri(context, contentUri))
        } catch (e: Exception) {
            try {
                File(contentUri.path)
            } catch (e: Exception) {
                null
            }
        }
    }

    fun getFile(path: String): File? {
        return try {
            File(path)
        } catch (e: Exception) {
            null
        }
    }

    private fun getRealPathFromUri(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        return try {
            val pro = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, pro, null, null, null)
            val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(columnIndex)
        } catch (e: Exception) {
            contentUri.path ?: ""
        } finally {
            cursor?.close()
        }
    }

    /**检查sd卡状态*/
    private fun checkSDStatus(): Boolean {
        //判断sd是否可用
        val sdStatus = Environment.getExternalStorageState()
        return sdStatus == Environment.MEDIA_MOUNTED
    }

    /**获得下载保存默认地址*/
    fun getDefaultDownLoadPath(): String {
        return if (checkSDStatus()) Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + File.separator else ""
    }

    /**从url中，获得默认文件名*/
    fun getDefaultDownLoadFileName(url: String?): String {
        if (url == null || url.isEmpty()) return "unknown"
        val nameStart = url.lastIndexOf('/') + 1
        return url.substring(nameStart)
    }

    fun saveFile(file: File, filePath: String?, fileName: String?): File? {
        var resultFile: File? = null
        val saveFile: File? = if (TextUtils.isEmpty(filePath)) {
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absoluteFile, "昆明文件")
        } else {
            File(filePath)
        }

        if (!saveFile!!.exists()) {
            saveFile.mkdirs()
        }

        try {
            resultFile = FileUtils.copyFileToOtherFolder(file, saveFile, fileName
                    ?: System.currentTimeMillis().toString() + file.extension)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return resultFile
    }

    @Throws(FileNotFoundException::class)
    fun callSystemUpdate(context: Context, file: File?) {
        if (file == null || !file.exists()) {
            throw FileNotFoundException()
        }
        MediaStore.Images.Media.insertImage(context.contentResolver,
                file.absolutePath, file.name, null)
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(File(file.path))))
    }

    fun getDocDownloadFile(context: Context): File {
        return File(context.externalCacheDir, "docs")
    }

    fun getFileType(paramString: String): String {
        var str = ""
        if (TextUtils.isEmpty(paramString)) {
            return str
        }
        val i = paramString.lastIndexOf('.')
        if (i <= -1) {
            return str
        }
        str = paramString.substring(i + 1)
        return str
    }

    fun save(file: File, str: String): Boolean {
        var result = false
        try {
            val fw = FileWriter(file)
            fw.flush()
            fw.write(str)
            fw.close()
            result = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    /**获取指定文件大小*/
    private fun getFileSize(file: File?): Long {
        var size: Long = 0
        if (file != null && file.exists()) {
            var fis: FileInputStream? = null
            try {
                fis = FileInputStream(file)
                size = fis.available().toLong()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (fis != null) {
                    try {
                        fis.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        } else {
        }
        return size / 1024
    }

    /**获取指定文件夹*/
    private fun getFileSizes(file: File?): Long {
        var size: Long = 0
        if (file != null && file.exists()) {
            for (f in file.listFiles()) {
                if (f != null && f.exists()) {
                    if (f.isDirectory) {
                        size += getFileSizes(f)
                    } else {
                        size += getFileSize(f)
                    }
                }
            }
        }
        return size
    }

    fun writeFile(inputStream: InputStream?, file: File): Boolean {
        var result = false
        val parentDir = file.parentFile
        if (parentDir.exists() || !parentDir.exists() && parentDir.mkdirs()) {
            if (!file.exists() || file.exists() && file.delete()) {
                var fos: FileOutputStream? = null
                try {
                    fos = FileOutputStream(file)
                    val b = ByteArray(1024)
                    var len: Int = inputStream!!.read(b)
                    while (len != -1) {
                        len = inputStream.read(b)
                        fos.write(b, 0, len)
                    }
                    result = true
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
        return result
    }

    @Throws(IOException::class)
    fun copyFileToOtherFolder(file: File, saveFolder: File, fileName: String): File? {
        if (!file.exists()) {
            return null
        }
        if (!saveFolder.exists() || !saveFolder.isDirectory) {
            saveFolder.mkdir()
        }
        val currentFile = File(saveFolder, fileName)
        val inputStream = FileInputStream(file)
        val fileOutputStream = FileOutputStream(currentFile)
        val buffer = ByteArray(1024)
        var byteRead: Int = inputStream.read(buffer)
        while (byteRead != -1) {
            byteRead = inputStream.read(buffer)
            fileOutputStream.write(buffer, 0, byteRead)
        }
        inputStream.close()
        fileOutputStream.close()
        return currentFile
    }

    fun getApkDownloadFile(context: Context): File {
        var file = File(context.externalCacheDir, "apk")
        if (!file.exists()) {
            file.mkdirs()
        }
        file = File(file, "uploadApp.apk")
        if (file.exists()) {
            file.delete()
        }
        return file
    }

    fun createMovieFile(): File? {
        return try {
            File(VIDEO_PATH, "视频-" + System.currentTimeMillis().toString() + ".mp4")
        } catch (e: Exception) {
            null
        }
    }

    /**保存图片*/
    @SuppressLint("SdCardPath")
    fun saveCroppedImage(bmp: Bitmap) {
        var file = File("/sdcard/Download")
        if (!file.exists()) {
            file.mkdir()
        }
        file = File("/sdcard/temp.jpg".trim())
        val fileName = file.name
        val mName = fileName.substring(0, fileName.lastIndexOf("."))
        val sName = fileName.substring(fileName.lastIndexOf("."))
        val newFilePath = "/sdcard/Download" + "/" + mName + "_cropped" + sName
        file = File(newFilePath)
        try {
            file.createNewFile()
            val fos = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**大图预览图片*/
    @SuppressLint("PrivateResource")
    fun previewImage(context: Context, url: String?) {
        if (TextUtils.isEmpty(url)) {
            ToastUtils.showToast(context, "文件链接错误")
            return
        }
        val dialog = Dialog(context, R.style.AlertDialog_AppCompat_Light)
        val imageView = ImageView(context)
        imageView.layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        // 设置Padding
        imageView.setPadding(20, 20, 20, 20)
        ImageLoaderUtils.display(context, imageView, url)
        dialog.setContentView(imageView)
        imageView.setOnClickListener {
            dialog.dismiss()
        }
        imageView.setOnLongClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setItems(arrayOf("保存本地")) { dialog: DialogInterface, _: Int ->
                // todo
                dialog.dismiss()
            }
            builder.setCancelable(true)
            builder.show()
            return@setOnLongClickListener true
        }
        dialog.show()
    }

    /**鲁班压缩*/
    fun zipFileByLuBan(context: Context, file: File, listener: OnTListener<File>) {
        val oldFile = File(file.path)
        Luban.with(context)
                .load(oldFile)
                .setCompressListener(object : OnCompressListener {
                    override fun onStart() {
                    }

                    override fun onSuccess(newFile: File?) {
                        listener.getItem(oldFile)
                    }

                    override fun onError(e: Throwable?) {

                    }
                }).launch()
    }

    /**转换文件大小*/
    fun formatFileSize(fileS: Long): String {
        val df = DecimalFormat("#.00")
        val wrongSize = "0B"
        if (fileS == 0L) {
            return wrongSize
        }
        return when {
            fileS < 1024 -> df.format(fileS.toDouble()) + "B"
            fileS < 1048576 -> df.format(fileS.toDouble() / 1024) + "KB"
            fileS < 1073741824 -> df.format(fileS.toDouble() / 1048576) + "MB"
            else -> df.format(fileS.toDouble() / 1073741824) + "GB"
        }
    }

    /**鲁班压缩*/
    fun zipFileByLuBan(context: Context, fileList: List<File>, listener: OnTListener<List<File>>) {
        val newFileList = arrayListOf<File>()
        for ((index, file) in fileList.withIndex()) {
            val oldFile = File(file.path)
            Luban.with(context)
                    .load(oldFile)
                    .setCompressListener(object : OnCompressListener {
                        override fun onStart() {
                        }

                        override fun onSuccess(newFile: File?) {
                            if (newFile != null) {
                                newFileList.add(index, newFile)
                            }
                            if (newFileList.size == fileList.size) {
                                listener.getItem(newFileList)
                            }
                        }

                        override fun onError(e: Throwable?) {
                        }
                    }).launch()
        }
    }
}
