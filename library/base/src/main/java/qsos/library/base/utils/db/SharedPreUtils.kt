package qsos.library.base.utils.db

import android.content.Context

/**
 * @author : 华清松
 * @date : 2018/9/4 10:56
 * @description : SharedPreferences 工具类
 */
object SharedPreUtils {

    private var preName = "qsos_spr"
    private const val SHARED_PRE = "SHARED_PRE"
    private const val COOKIE = "COOKIE"
    private const val USER_ID = "USER_ID"

    /**保存cookie数据*/
    fun saveCookie(context: Context, cookie: String) {
        val sp = context.getSharedPreferences(SHARED_PRE, Context.MODE_PRIVATE)
        sp.edit().putString(COOKIE, cookie).apply()
    }

    /**获取cookie数据*/
    fun getCookie(context: Context): String {
        val sp = context.getSharedPreferences(SHARED_PRE, Context.MODE_PRIVATE)
        return sp.getString(COOKIE, "")!!
    }

    /**保存用户 ID 数据*/
    fun saveUserId(context: Context, userId: String) {
        val sp = context.getSharedPreferences(SHARED_PRE, Context.MODE_PRIVATE)
        sp.edit().putString(USER_ID, userId).apply()
    }

    /**获取用户 ID 数据*/
    fun getUserId(context: Context): String {
        val sp = context.getSharedPreferences(SHARED_PRE, Context.MODE_PRIVATE)
        return sp.getString(USER_ID, "")!!
    }

    /**保存 String 类型数据*/
    fun saveStr(context: Context?, key: String, value: String?) {
        context?.getSharedPreferences(preName, Context.MODE_PRIVATE)?.edit()?.putString(key, value)?.apply()
    }

    /**获取 String 类型数据*/
    fun getStr(context: Context?, key: String): String? {
        val sp = context?.getSharedPreferences(preName, Context.MODE_PRIVATE)
        return sp?.getString(key, null)
    }

    /**保存 Boolean 类型数据*/
    fun saveBoolean(context: Context?, key: String, value: Boolean) {
        context?.getSharedPreferences(preName, Context.MODE_PRIVATE)?.edit()?.putBoolean(key, value)?.apply()
    }

    /**获取 Boolean 类型数据*/
    fun getBoolean(context: Context?, key: String): Boolean {
        val sp = context?.getSharedPreferences(preName, Context.MODE_PRIVATE)
        return sp?.getBoolean(key, true) ?: true
    }

}