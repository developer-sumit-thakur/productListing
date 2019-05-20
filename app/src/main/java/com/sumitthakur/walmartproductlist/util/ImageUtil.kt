package com.sumitthakur.walmartproductlist.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sumitthakur.walmartproductlist.api.BackendService

/**
 * utils class to load image
 * @author Sumit.T
 */

class ImageUtil {
    private fun getImageUrl(imageUrl: String): String {
        return BackendService.BASE_URL + "/" + imageUrl
    }

    fun loadImage(context: Context?, imageView: ImageView, imageUrl: String) {
        context?.apply {
            Glide.with(this)
                .load(getImageUrl(imageUrl))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageView?.apply {
                            imageView.setImageDrawable(resource)
                        }
                        return false
                    }
                })
                .submit()
        }
    }
}