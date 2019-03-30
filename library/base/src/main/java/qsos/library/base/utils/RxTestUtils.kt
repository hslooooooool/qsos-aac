package qsos.library.base.utils

import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

/**
 * @author : 华清松(姓名) 821034742@qq.com(邮箱) hslooooooool(微信)
 * @date : 2018/5/22 0022
 * @description :  Rxjava IO 异步线程转换同步,便于单元测试
 */
object RxTestUtils {
    fun asyncToSync() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }
}
