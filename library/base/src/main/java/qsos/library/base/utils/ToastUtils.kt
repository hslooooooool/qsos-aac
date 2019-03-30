package qsos.library.base.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

object ToastUtils {

    fun showToast(context: Context, tips: String) {
        val toast = Toast.makeText(context, tips, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}
