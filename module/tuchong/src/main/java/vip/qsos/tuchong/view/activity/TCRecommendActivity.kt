package vip.qsos.tuchong.view.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import qsos.library.base.routerpath.HomePath
import qsos.module.common.aac.BaseModuleActivity
import vip.qsos.tuchong.R
import vip.qsos.tuchong.view.fragment.TCRecommendFragment

/**
 * @author : 华清松
 * @date : 2019/1/24
 * @description : 图虫推荐页
 */
@Route(group = HomePath.GROUP, path = HomePath.TC_RECOMMEND)
class TCRecommendActivity : BaseModuleActivity() {

    private var tcRecommendFragment = TCRecommendFragment()

    override val layoutId = R.layout.tc_activity_main
    override val reload = true

    override fun initData(savedInstanceState: Bundle?) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        transaction.replace(R.id.frg_tc, tcRecommendFragment)
        transaction.commit()
    }

    override fun initView() {

    }

    override fun getData() {

    }

}