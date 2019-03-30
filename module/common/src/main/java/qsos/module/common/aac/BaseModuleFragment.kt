package qsos.module.common.aac

import qsos.library.base.base.BaseFragment
import qsos.library.base.base.BaseView
import qsos.library.base.entity.http.ApiException
import qsos.library.base.entity.http.ErrorCode
import qsos.library.widget.dialog.PopupWindowUtils

/**
 * @author : 华清松
 * @date : 2018/10/8
 * @description : 业务模块公共 Fragment
 */
abstract class BaseModuleFragment : BaseFragment() {

    override fun showProgress(msg: String) {
        changeView(BaseView.STATE.LOADING)
    }

    override fun closeProgress() {
        changeView(BaseView.STATE.OK)
    }

    override fun showDialog(msg: String?) {
        PopupWindowUtils.showTextOk(mContext!!, msg, null)
    }

    override fun showHttpError(httpException: ApiException) {
        closeProgress()
        when (httpException.code) {
            ErrorCode.UNAUTHORIZED -> {
                changeView(BaseView.STATE.NET_ERROR)
            }
            ErrorCode.NETWORK_ERROR -> {
                changeView(BaseView.STATE.NOT_NET)
            }
            ErrorCode.NOT_FOUND -> {
                changeView(BaseView.STATE.NOT_FOUND)
            }
            ErrorCode.HTTP_ERROR -> {
                changeView(BaseView.STATE.NET_ERROR)
            }
            ErrorCode.RESULT_NULL -> {
                changeView(BaseView.STATE.RESULT_NULL)
            }
            else -> {
                showToast(httpException.displayMessage)
                changeView(BaseView.STATE.NET_ERROR)
            }
        }
    }
}