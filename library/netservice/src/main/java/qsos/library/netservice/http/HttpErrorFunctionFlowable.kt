package qsos.library.netservice.http

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：网络请求异常处理
 */
class HttpErrorFunctionFlowable<T> : Function<Throwable, Flowable<T>> {

    @Throws(Exception::class)
    override fun apply(throwable: Throwable): Flowable<T> {
        return Flowable.error(ApiExceptionServiceImpl.handleException(throwable))
    }
}