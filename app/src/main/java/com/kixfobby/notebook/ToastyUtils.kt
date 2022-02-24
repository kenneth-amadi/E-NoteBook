package com.kixfobby.notebook

import androidx.annotation.ColorInt
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import android.graphics.PorterDuff
import android.graphics.drawable.NinePatchDrawable
import android.os.Build
import android.content.*
import android.view.*

internal object ToastyUtils {
    fun tintIcon(drawable: Drawable, @ColorInt tintColor: Int): Drawable {
        drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        return drawable
    }

    fun tint9PatchDrawableFrame(context: Context, @ColorInt tintColor: Int): Drawable {
        val toastDrawable = getDrawable(context, R.drawable.toast_frame) as NinePatchDrawable?
        return tintIcon(toastDrawable!!, tintColor)
    }

    fun setBackground(view: View, drawable: Drawable?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) view.background =
            drawable else view.setBackgroundDrawable(drawable)
    }

    fun getDrawable(context: Context, @DrawableRes id: Int): Drawable? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) context.getDrawable(id) else context.resources.getDrawable(id)
    }
}