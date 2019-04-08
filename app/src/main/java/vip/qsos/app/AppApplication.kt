package vip.qsos.app

import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.FalsifyFooter
import com.scwang.smartrefresh.layout.header.FalsifyHeader
import qsos.module.common.R
import qsos.module.common.ModelApplication

/**
 * @author : 华清松
 * @date : 2018/10/8 17:45
 * @description : Application 类
 */
internal class AppApplication : ModelApplication() {
    init {
        // 设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.white, R.color.black)
            FalsifyHeader(context)
        }
        // 设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.red, R.color.black)
            FalsifyFooter(context)
        }
    }
}