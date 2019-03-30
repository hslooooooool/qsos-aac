package qsos.library.netservice.http.interceptor

import qsos.library.base.entity.http.ApiException
import qsos.library.base.entity.http.ErrorCode
import io.reactivex.observers.DisposableObserver

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：请求错误处理类
 */
abstract class ApiExceptionService<T> : DisposableObserver<T>() {

    protected abstract fun onError(ex: ApiException)

    override fun onError(e: Throwable) {
        if (e is ApiException) {
            onError(e)
        } else {
            onError(ApiException(e, ErrorCode.UNKNOWN))
        }
    }
}
