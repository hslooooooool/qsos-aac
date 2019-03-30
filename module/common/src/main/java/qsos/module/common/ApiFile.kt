package qsos.module.common

import io.reactivex.Flowable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import qsos.library.base.entity.BaseResult
import qsos.library.base.entity.app.FileEntity
import retrofit2.http.*
import java.util.*


/**
 * @author : 华清松
 * @date : 2018/10/24 17:23
 * @description : 文件 接口类
 */
interface ApiFile {

    @Multipart
    @POST("/yf/file/process/up_load_file")
    fun uploadFile(
            @Part part: MultipartBody.Part
    ): Flowable<BaseResult<List<FileEntity>>>

    @Multipart
    @POST("/yf/file/process/up_load_files")
    fun uploadFiles(
            @Part files: ArrayList<MultipartBody.Part>
    ): Flowable<BaseResult<List<FileEntity>>>

    @Streaming
    @GET
    fun downloadFile(
            @Url url: String
    ): Flowable<ResponseBody>

}