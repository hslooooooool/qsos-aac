package qsos.library.base.base

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import qsos.library.base.BuildConfig
import qsos.library.base.utils.db.SharedPreUtils
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * @author : 华清松
 * @date : 2018/10/8
 * @description : BaseApplication
 */
open class BaseApplication : MultiDexApplication() {

    companion object {
        var appContext: BaseApplication? = null
        var buildFinish: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        if (SharedPreUtils.getBoolean(base, "FirstLaunch")) {
            SharedPreUtils.saveBoolean(base, "FirstLaunch", true)
            // 首次启动
            Thread(Runnable {
                MultiDex.install(this)
                buildFinish = true
            }).start()
        } else {
            // 非首次启动
            MultiDex.install(this)
            buildFinish = true
        }
    }

}
