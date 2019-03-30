package qsos.module.common

import io.reactivex.Observable
import qsos.library.base.entity.BaseResult
import qsos.library.base.entity.app.UserEntity
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author : 华清松
 * @date : 2018/10/24 17:23
 * @description : 文件 接口类
 */
interface ApiBase {

    @GET("/user/info")
    fun getUser(
            @Query("userId") userId: String
    ): Observable<BaseResult<UserEntity>>

}