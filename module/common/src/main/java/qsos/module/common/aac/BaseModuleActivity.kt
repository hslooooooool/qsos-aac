package qsos.module.common.aac

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v4.app.ActivityCompat
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import qsos.library.base.base.BaseActivity
import qsos.library.base.base.BaseView
import qsos.library.base.callback.OnTListener
import qsos.library.base.entity.http.ApiException
import qsos.library.base.entity.http.ErrorCode
import qsos.library.widget.dialog.PopupWindowUtils

/**
 * @author : 华清松
 * @date : 2018/10/8
 * @description : 业务模块公共 Activity
 */
@SuppressLint("CheckResult")
abstract class BaseModuleActivity : BaseActivity() {
    override var statusBarColor: Int? = null
    private var permission: RxPermissions? = null

    override fun onCreate(bundle: Bundle?) {
        permission = RxPermissions(this)
        super.onCreate(bundle)
    }

    override fun showProgress(msg: String) {
        changeView(BaseView.STATE.LOADING)
    }

    override fun closeProgress() {
        changeView(BaseView.STATE.OK)
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

    override fun showDialog(msg: String?) {
        PopupWindowUtils.showTextOk(mContext!!, msg, null)
    }

    /**请求权限成功后执行*/
    private val allowablePermissionRunnable = HashMap<Int, Runnable>()

    /**请求权限失败后执行*/
    private val disAllowablePermissionRunnable = HashMap<Int, Runnable>()

    /**检查权限*/
    fun checkPermission(@NonNull permission: String, listener: OnTListener<Boolean>?) {
        requestPermission(1, permission,
                Runnable {
                    listener?.getItem(true)
                },
                Runnable {
                    listener?.getItem(false)
                    showToast("授权失败，无法使用此功能")
                })
    }

    /**检查权限*/
    fun checkPermission(@NonNull permissions: Array<String>, listener: OnTListener<Boolean>?) {
        requestPermission(1, permissions,
                Runnable {
                    listener?.getItem(true)
                },
                Runnable {
                    listener?.getItem(false)
                    showToast("授权失败，无法使用此功能")
                })
    }

    /**
     * 请求权限
     *
     * @param permission           请求的权限,请求授权的唯一标识
     * @param allowableRunnable    同意授权后的操作
     * @param disAllowableRunnable 禁止权限后的操作
     */
    private fun requestPermission(requestCode: Int, @NonNull permission: String, @Nullable allowableRunnable: Runnable?, @Nullable disAllowableRunnable: Runnable?) {

        if (allowableRunnable == null) {
            throw IllegalArgumentException("allowableRunnable == null")
        }
        allowablePermissionRunnable[requestCode] = allowableRunnable
        if (disAllowableRunnable != null) {
            disAllowablePermissionRunnable[requestCode] = disAllowableRunnable
        }

        // 检查是否拥有权限
        val checkCallPhonePermission = ActivityCompat.checkSelfPermission(applicationContext, permission)
        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
            // 弹出对话框接收权限
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            return
        } else {
            allowableRunnable.run()
        }
    }

    private var permissionCheck = true
    /**
     * 请求权限
     *
     * @param permissions           请求的权限,请求授权的唯一标识
     * @param allowableRunnable    同意授权后的操作
     * @param disAllowableRunnable 禁止权限后的操作
     */
    private fun requestPermission(requestCode: Int, @NonNull permissions: Array<String>, @Nullable allowableRunnable: Runnable?, @Nullable disAllowableRunnable: Runnable?) {
        if (allowableRunnable == null) {
            throw IllegalArgumentException("allowableRunnable == null")
        }
        allowablePermissionRunnable[requestCode] = allowableRunnable
        if (disAllowableRunnable != null) {
            disAllowablePermissionRunnable[requestCode] = disAllowableRunnable
        }
        var pass = true
        for (p in permissions) {
            val checkCallPhonePermission = ActivityCompat.checkSelfPermission(applicationContext, p)
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                pass = false
                ActivityCompat.requestPermissions(this, permissions, requestCode)
            }
        }
        if (pass) {
            allowableRunnable.run()
        }
    }

    /*权限请求回调*/
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isEmpty()) {
            return
        }
        for (r in grantResults) {
            if (r != PackageManager.PERMISSION_GRANTED) {
                permissionCheck = false
            }
        }
        if (permissionCheck) {
            val allowRun = allowablePermissionRunnable[requestCode]
            allowRun?.run()
        } else {
            val disallowRun = disAllowablePermissionRunnable[requestCode]
            disallowRun?.run()
        }
    }

    private fun onError(): Consumer<Throwable> {
        return Consumer { e ->
            e.printStackTrace()
            showDialog("选择失败 Failed: " + e.toString())
        }
    }

}
