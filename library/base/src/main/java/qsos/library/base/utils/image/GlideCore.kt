package qsos.library.base.utils.image

import android.content.Context
import android.support.v4.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideCore : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {

    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDiskCache(ExternalPreferredCacheDiskCacheFactory(context,
                diskCacheFolderName(context),
                diskCacheSizeBytes().toLong()))
                .setMemoryCache(LruResourceCache(memoryCacheSizeBytes().toLong()))
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    private fun memoryCacheSizeBytes(): Int {
        // 20 MB
        return 1024 * 1024 * 20
    }

    private fun diskCacheSizeBytes(): Int {
        // 512 MB
        return 1024 * 1024 * 512
    }

    private fun diskCacheFolderName(context: Context): String {
        return ContextCompat.getCodeCacheDir(context).path + "/qsos"
    }
}
