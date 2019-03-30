package qsos.library.base.utils.activity

import android.app.Activity

/**
 * @author : 华清松
 * @date : 2018/10/11 14:30
 * @description : Activity 管理类
 */
open class ActivityUtils private constructor() {

    companion object {
        val instance: ActivityUtils by lazy { ActivityUtils() }
    }

    /**Activity集合，用来同意管理activity，方便一键退出*/
    private var activityList = arrayListOf<Activity>()

    /**添加activity*/
    fun addActivity(activity: Activity) {
        activityList.add(activity)
    }

    /**移除某个 activity*/
    fun finishSingle(activity: Activity) {
        activityList.remove(activity);
        if (!activity.isFinishing) {
            activity.finish();
        }
    }

    /**移除所有的 activity*/
    fun finishAll() {
        for (activity in activityList) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**清除掉除了自己之外的 activity*/
    fun finishAllButNotMe(activity: Activity) {
        for (ac in activityList) {
            if (ac != activity && !ac.isFinishing) {
                // 如果不是，finish 掉
                ac.finish();
            }
        }
    }
}
