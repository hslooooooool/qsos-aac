package qsos.library.netservice.http

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：观察者装饰器，使用与JUNIT进行测试时模拟网络请求返回
 */
object ObservableDecorator {

    fun <T> decorate(observable: Observable<T>): Observable<T> {
        return if (ApiEngine.isUnitTest) {
            observable.subscribeOn(Schedulers.trampoline()).observeOn(Schedulers.trampoline())
        } else {
            observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .delay(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
        }
    }
}
