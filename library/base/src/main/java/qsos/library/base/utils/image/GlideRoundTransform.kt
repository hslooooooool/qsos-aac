package qsos.library.base.utils.image

import android.content.res.Resources
import android.graphics.*

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop

import java.security.MessageDigest
/**
 * @author : 华清松
 * @date : 2018/12/7
 * @description : glide 转换圆角图片
 */
class GlideRoundTransform(dp: Float) : CenterCrop() {

    init {
        radius = Resources.getSystem().displayMetrics.density * dp
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        val transform = super.transform(pool, toTransform, outWidth, outHeight)
        return roundCrop(pool, transform)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }

    companion object {
        private var radius = 0f

        private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
            if (source == null) return null

            var result: Bitmap? = pool.get(source.width, source.height, Bitmap.Config.ARGB_8888)
            if (result == null) {
                result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
            }

            val canvas = Canvas(result!!)
            val paint = Paint()
            paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.isAntiAlias = true
            val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
            canvas.drawRoundRect(rectF, radius, radius, paint)
            return result
        }
    }
}