package qsos.library.netservice.http.upload

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * @author : 华清松
 * @date : 2018/12/5
 * @description : 上传监听
 */
class UploadRequestBody(private val mFile: File) : RequestBody() {

    private var mUploadOnSubscribe: UploadOnSubscribe? = null

    fun setUploadOnSubscribe(uploadOnSubscribe: UploadOnSubscribe) {
        this.mUploadOnSubscribe = uploadOnSubscribe
    }

    override fun contentType(): MediaType? {
        return MediaType.parse("multipart/form-data")
    }

    override fun contentLength(): Long {
        return mFile.length()
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val buffer = ByteArray(2048)
        FileInputStream(mFile).use { input ->
            var read: Int = -1
            while (input.read(buffer).also { read = it } != -1) {
                if (mUploadOnSubscribe != null) {
                    mUploadOnSubscribe!!.onRead(read.toLong())
                }
                sink.write(buffer, 0, read)
            }
        }
    }

}
