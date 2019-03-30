package qsos.library.base.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_base.*
import org.reactivestreams.Subscription
import qsos.library.base.R
import qsos.library.base.utils.LogUtil
import qsos.library.base.utils.ToastUtils
import qsos.library.base.utils.activity.ActivityUtils

/**
 * @author : 华清松
 * @date : 2019/3/30
 * @description : Base Activity
 */
abstract class BaseActivity : AppCompatActivity(), BaseView, View.OnClickListener {

    private var subscription: Subscription? = null

    /**设置视图ID*/
    abstract val layoutId: Int?

    abstract var statusBarColor: Int?
    /**视图重载是否重新加载数据*/
    abstract val reload: Boolean
    override val defLayoutId: Int = R.layout.activity_default

    override var isActive: Boolean = false
        protected set(value) {
            field = value
        }

    final override var mContext: Context? = null
        protected set(value) {
            field = value
        }

    /*注意调用顺序*/

    /**初始化数据*/
    abstract fun initData(savedInstanceState: Bundle?)

    /**初始化试图*/
    abstract fun initView()

    /**获取数据*/
    abstract fun getData()

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        LogUtil.i("启动:$localClassName")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate(bundle: Bundle?) {
        LogUtil.i("创建:$localClassName")
        super.onCreate(bundle)
        mContext = this
        // 全部竖屏显示
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ARouter.getInstance().inject(this)

        initData(bundle)
        if (layoutId == null) {
            setContentView(defLayoutId)
        } else {
            setContentView(layoutId!!)
            initView()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = statusBarColor
                    ?: ContextCompat.getColor(this, R.color.colorPrimary)
        }

        ll_base_not_net?.setOnClickListener(this)
        ll_base_net_error?.setOnClickListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {
        super.onStart()
        isActive = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
        super.onResume()
        LogUtil.i("当前:$localClassName")
        if (reload) {
            getData()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {
        super.onPause()
        LogUtil.i("暂停:$localClassName")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {
        super.onStop()
        isActive = false
    }

    override fun finish() {
        super.finish()
        LogUtil.i("结束:$localClassName")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i("销毁:$localClassName")
        if (subscription != null) {
            subscription!!.cancel()
        }

        ActivityUtils.instance.finishSingle(this)
    }

    override fun showToast(msg: String?) {
        ToastUtils.showToast(this, if (TextUtils.isEmpty(msg)) "没有信息" else msg!!)
    }

    override fun getNowTime(): Long {
        return System.currentTimeMillis()
    }

    override fun showDialog(msg: String?) {
        if (TextUtils.isEmpty(msg)) {
            return
        }
        Toast.makeText(mContext, "$msg", Toast.LENGTH_SHORT).show()
    }

    override fun finishThis() {
        finish()
    }

    fun hideKeyboard() {
        val imm: InputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            if (this.currentFocus?.windowToken != null) {
                imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }

    fun changeView(state: BaseView.STATE) {
        ll_base_not_net?.visibility = View.GONE
        ll_base_404?.visibility = View.GONE
        ll_base_net_error?.visibility = View.GONE
        ll_base_loading?.visibility = View.GONE
        ll_base_null?.visibility = View.GONE
        when (state) {
            BaseView.STATE.NOT_NET -> {
                ll_base_not_net?.visibility = View.VISIBLE
            }
            BaseView.STATE.LOADING -> {
                ll_base_loading?.visibility = View.VISIBLE
            }
            BaseView.STATE.NET_ERROR -> {
                ll_base_net_error?.visibility = View.VISIBLE
            }
            BaseView.STATE.NOT_FOUND -> {
                ll_base_404?.visibility = View.VISIBLE
            }
            BaseView.STATE.RESULT_NULL -> {
                ll_base_null?.visibility = View.VISIBLE
            }
            BaseView.STATE.OK -> {
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_base_not_net, R.id.ll_base_net_error -> {
                changeView(BaseView.STATE.LOADING)
                getData()
            }
        }
    }

}
