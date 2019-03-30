package vip.qsos.app.view.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import io.reactivex.schedulers.Schedulers
import qsos.library.base.base.BaseApplication
import qsos.library.base.routerpath.AppPath
import qsos.library.base.utils.activity.ActivityUtils
import qsos.module.common.aac.BaseModuleActivity
import vip.qsos.app.R
import java.util.concurrent.TimeUnit

/**
 * @author : 华清松
 * @date : 2018/10/30
 * @description : 闪屏界面
 */
@Route(group = AppPath.GROUP, path = AppPath.SPLASH)
class SplashActivity : BaseModuleActivity() {

    override val layoutId = R.layout.activity_splash
    override val reload = false

    override fun initData(savedInstanceState: Bundle?) {
        ActivityUtils.instance.addActivity(this)
    }

    override fun initView() {
        ActivityUtils.instance.finishAllButNotMe(this)
        statusBarColor = ContextCompat.getColor(this, R.color.white)

        Schedulers.newThread().scheduleDirect({
            while (BaseApplication.buildFinish) {
                ARouter.getInstance().build(AppPath.MAIN).navigation()
                finishThis()
                break
            }
        }, 800, TimeUnit.MILLISECONDS)

    }

    override fun getData() {
    }

}