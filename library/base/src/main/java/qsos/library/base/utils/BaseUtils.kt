package qsos.library.base.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.security.MessageDigest
import kotlin.experimental.and

/**
 * @author : 华清松
 * @date : 2018/11/28
 * @description : 工具类
 */
class BaseUtils {
    companion object {
        private var mToast: Toast? = null

        /**
         * 设置hint大小
         */
        fun setViewHintSize(context: Context, size: Int, v: TextView, res: Int) {
            val ss = SpannableString(getResources(context).getString(res))
            // 新建一个属性对象,设置文字的大小
            val ass = AbsoluteSizeSpan(size, true)
            // 附加属性到文本
            ss.setSpan(ass, 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            // 设置hint，一定要进行转换,否则属性会消失
            v.hint = SpannedString(ss)
        }

        /**
         * dip转pix
         */
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = getResources(context).displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * sp
         */
        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * 获得资源
         */
        private fun getResources(context: Context): Resources {
            return context.resources
        }

        /**
         * 得到字符数组
         */
        fun getStringArray(context: Context, id: Int): Array<String> {
            return getResources(context).getStringArray(id)
        }

        /**
         * pix转dip
         */
        fun pix2dip(context: Context, pix: Int): Int {
            val densityDpi = getResources(context).displayMetrics.density
            return (pix / densityDpi + 0.5f).toInt()
        }

        /**
         * 从 dimens 中获得尺寸
         *
         * @param context
         * @param id
         * @return
         */
        fun getDimens(context: Context, id: Int): Int {
            return getResources(context).getDimension(id).toInt()
        }

        /**
         * 从 dimens 中获得尺寸
         *
         * @param context
         * @param dimenName
         * @return
         */
        fun getDimens(context: Context, dimenName: String): Float {
            return getResources(context).getDimension(getResources(context).getIdentifier(dimenName, "dimen", context.packageName))
        }

        /**
         * 从String 中获得字符
         *
         * @return
         */

        fun getString(context: Context, stringID: Int): String {
            return getResources(context).getString(stringID)
        }

        /**
         * 从String 中获得字符
         *
         * @return
         */

        fun getString(context: Context, strName: String): String {
            return getString(context, getResources(context).getIdentifier(strName, "string", context.packageName))
        }

        /**
         * 根据 layout 名字获得 id
         *
         * @param layoutName
         * @return
         */
        fun findLayout(context: Context, layoutName: String): Int {
            return getResources(context).getIdentifier(layoutName, "layout", context.packageName)
        }

        /**
         * 获得状态栏的高度
         *
         * @return
         */
        fun getStatusBarHeight(context: Context): Int {
            return context.resources.getDimensionPixelSize(context.resources.getIdentifier("status_bar_height", "dimen", "android"))
        }

        /**
         * 获得导航栏的高度
         *
         * @return
         */
        fun getNavigationBarHeight(context: Context): Int {
            val resIdNavigationBar = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return if (resIdNavigationBar > 0) 0 else context.resources.getDimensionPixelSize(resIdNavigationBar)
        }

        /**
         * 填充view
         *
         * @param detailScreen
         * @return
         */
        fun inflate(context: Context, detailScreen: Int): View {
            return View.inflate(context, detailScreen, null)
        }

        /**
         * 单例 toast
         *
         * @param string
         */
        fun makeText(context: Context, string: String) {
            if (mToast == null) {
                mToast = Toast.makeText(context.applicationContext, string, Toast.LENGTH_SHORT)
            } else {
                mToast!!.setText(string)
            }
            mToast!!.show()
        }

        /**
         * 跳转界面 3
         *
         * @param activity
         * @param homeActivityClass
         */
        @SuppressWarnings("unused")
        fun startActivity(activity: Activity, homeActivityClass: Class<*>) {
            val intent = Intent(activity.applicationContext, homeActivityClass)
            activity.startActivity(intent)
        }

        /**
         * 跳转界面 4
         *
         * @param
         */
        @SuppressWarnings("unused")
        fun startActivity(activity: Activity, intent: Intent) {
            activity.startActivity(intent)
        }

        /**
         * 获得屏幕的宽度
         *
         * @return
         */
        @SuppressWarnings("unused")
        fun getScreenWidth(context: Context): Int {
            return getResources(context).displayMetrics.widthPixels
        }

        /**
         * 获得屏幕的高度
         *
         * @return
         */
        @SuppressWarnings("unused")
        fun getScreenHeight(context: Context): Int {
            return getResources(context).displayMetrics.heightPixels
        }

        /**
         * 移除子布局
         *
         * @param view
         */
        @SuppressWarnings("unused")
        fun removeChild(view: View) {
            val parent = view.parent
            if (parent is ViewGroup) {
                parent.removeView(view)
            }
        }

        @SuppressWarnings("unused")
        fun isEmpty(obj: Any?): Boolean {
            return obj == null
        }

        /**
         * MD5
         *
         * @param string
         * @return
         * @throws Exception
         */
        @SuppressWarnings("unused")
        fun encodeToMD5(string: String): String {
            var hash = ByteArray(0)
            try {
                hash = MessageDigest.getInstance("MD5").digest(
                        string.toByteArray(charset("UTF-8")))
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val hex = StringBuilder(hash.size * 2)
            for (b in hash) {
                if (b and 0xFF.toByte() < 0x10) {
                    hex.append("0")
                }
                hex.append(Integer.toHexString((b and 0xFF.toByte()).toInt()))
            }
            return hex.toString()
        }

        /**
         * 全屏,并且沉侵式状态栏
         *
         * @param activity
         */
        @SuppressWarnings("unused")
        fun statuInScreen(activity: Activity) {
            val attrs = activity.window.attributes
            attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
            activity.window.attributes = attrs
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        /**
         * 只沉浸状态栏
         *
         * @param activity
         */
        @SuppressWarnings("unused")
        fun hideStatusBar(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT
                //          NavigationBar颜色
                //            window.setNavigationBarColor(getResources(activity).getColor(0));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }

        /**
         * 沉浸导航栏
         *
         * @param activity
         */
        @SuppressWarnings("unused")
        fun hideNavigationBar(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                window.navigationBarColor = Color.TRANSPARENT
            }
        }

        /**
         * 获取当前亮度
         */
        @SuppressWarnings("unused")
        fun getBrightness(activity: Activity?): Float {
            return activity?.window?.attributes?.screenBrightness ?: -1f
        }

        /**
         * 调节屏幕亮度(当前界面 不需要权限)
         */
        @SuppressWarnings("unused")
        fun setBrightness(activity: Activity?, _percent: Float) {
            var percent = _percent
            if (activity == null) {
                return
            }
            percent = if (percent > 1f) 1f else percent
            percent = if (percent < 0f) 0f else percent
            val lp = activity.window.attributes
            lp.screenBrightness = percent
            activity.window.attributes = lp
        }

        /**
         * 打开软键盘
         */
        @SuppressWarnings("unused")
        fun openKeyBord(context: Context, mEditText: EditText) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }

        /**
         * 关闭软键盘
         */
        @SuppressWarnings("unused")
        fun closeKeyBord(context: Context, mEditText: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
        }

        @SuppressWarnings("unused")
        fun getSettingIntent(context: Context): Intent {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("package:" + context.packageName)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            return intent
        }

        /**从url获取文件后缀*/
        fun getFileExtension(url: String): String {
            var str = ""
            if (TextUtils.isEmpty(url)) {
                return str
            }
            val urlstr1 = url.split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            val i = urlstr1.lastIndexOf("/")
            if (i <= -1) {
                return str
            }
            val urlstr2 = urlstr1.substring(i + 1)
            val indexOf = urlstr2.lastIndexOf(".")
            if (indexOf <= -1) {
                return str
            }
            str = urlstr2.substring(indexOf + 1)
            return str
        }

        /**从url获取文件名*/
        fun getFileNameHasExtension(url: String): String {
            var str = ""

            if (TextUtils.isEmpty(url)) {
                return str
            }

            val urlStr = url.split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            val i = urlStr.lastIndexOf("/")
            if (i <= -1) {
                return str
            }
            str = urlStr.substring(i + 1)
            return str
        }
    }
}