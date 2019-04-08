package vip.qsos.app.view.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_main.*
import qsos.library.base.routerpath.AppPath
import qsos.library.base.utils.activity.ActivityUtils
import qsos.module.common.aac.BaseModuleActivity
import qsos.module.common.view.adapter.NavigationPagerAdapter
import vip.qsos.app.R
import vip.qsos.tuchong.view.fragment.TCRecommendFragment
import vip.qsos.widgets.view.WidgetsFragment

/**
 * @author : 华清松
 * @date : 2018/10/8
 * @description : 主界面
 */
@Route(group = AppPath.GROUP, path = AppPath.MAIN)
class MainActivity : BaseModuleActivity() {

    private var viewList = arrayListOf<Fragment>()
    private var mTitles = arrayListOf<String>()

    private var tcRecommendFragment = TCRecommendFragment()
    private var widgetsFragment = WidgetsFragment()

    override fun initData(savedInstanceState: Bundle?) {
        ActivityUtils.instance.addActivity(this)
        ActivityUtils.instance.finishAllButNotMe(this)

        mTitles.add("控件")
        viewList.add(widgetsFragment)

        mTitles.add("图虫")
        viewList.add(tcRecommendFragment)

    }

    override fun initView() {
        statusBarColor = ContextCompat.getColor(this, R.color.white)
        vp_main.adapter = NavigationPagerAdapter(supportFragmentManager, viewList, mTitles)
        vp_main.offscreenPageLimit = viewList.size

        tl_main.setupWithViewPager(vp_main)

        getData()
    }

    override fun getData() {}

    override val layoutId: Int = R.layout.activity_main
    override val reload: Boolean = false
}
