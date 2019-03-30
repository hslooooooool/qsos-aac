package qsos.library.netservice.http

import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：网络请求异常处理
 */
class HttpErrorFunc<T> : Function<Throwable, Observable<T>> {

    @Throws(Exception::class)
    override fun apply(throwable: Throwable): Observable<T> {
        return Observable.error(ApiExceptionServiceImpl.handleException(throwable))
    }

}