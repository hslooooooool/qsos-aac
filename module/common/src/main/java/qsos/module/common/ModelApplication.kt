package qsos.module.common

import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import qsos.library.base.base.BaseApplication
import qsos.module.common.R

/**
 * @author : 华清松
 * @date : 2018/10/26 19:27
 * @description : 模块 Application
 */
abstract class ModelApplication : BaseApplication() {
    init {
        // 设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.white, R.color.black)
            ClassicsHeader(context) as RefreshHeader
        }
        // 设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.white, R.color.black)
            ClassicsFooter(context)
        }
    }
}