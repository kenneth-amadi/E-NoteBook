package com.kixfobby.notebook

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.content.*
import android.graphics.Color
import android.view.*
import android.widget.*
import androidx.annotation.*

@SuppressLint("InflateParams")
object Toasty {
    @ColorInt
    private var DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")

    @ColorInt
    private var ERROR_COLOR = Color.parseColor("#D50000")

    @ColorInt
    private var INFO_COLOR = Color.parseColor("#3F51B5")

    @ColorInt
    private var SUCCESS_COLOR = Color.parseColor("#388E3C")

    @ColorInt
    private var WARNING_COLOR = Color.parseColor("#FFA900")

    @ColorInt
    private val NORMAL_COLOR = Color.parseColor("#353A3E")
    private val LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
    private var currentTypeface = LOADED_TOAST_TYPEFACE
    private var textSize = 16 // in SP
    private var tintIcon = true
    @CheckResult
    fun normal(context: Context, @StringRes message: Int): Toast? {
        return normal(context, context.getString(message), Toast.LENGTH_SHORT, null, false)
    }

    @CheckResult
    fun normal(context: Context, message: CharSequence): Toast? {
        return normal(context, message, Toast.LENGTH_SHORT, null, false)
    }

    @CheckResult
    fun normal(context: Context, @StringRes message: Int, icon: Drawable?): Toast? {
        return normal(context, context.getString(message), Toast.LENGTH_SHORT, icon, true)
    }

    @CheckResult
    fun normal(context: Context, message: CharSequence, icon: Drawable?): Toast? {
        return normal(context, message, Toast.LENGTH_SHORT, icon, true)
    }

    @CheckResult
    fun normal(context: Context, @StringRes message: Int, duration: Int): Toast? {
        return normal(context, context.getString(message), duration, null, false)
    }

    @CheckResult
    fun normal(context: Context, message: CharSequence, duration: Int): Toast? {
        return normal(context, message, duration, null, false)
    }

    @CheckResult
    fun normal(
        context: Context, @StringRes message: Int, duration: Int,
        icon: Drawable?
    ): Toast? {
        return normal(context, context.getString(message), duration, icon, true)
    }

    @CheckResult
    fun normal(
        context: Context, message: CharSequence, duration: Int,
        icon: Drawable?
    ): Toast? {
        return normal(context, message, duration, icon, true)
    }

    @CheckResult
    fun normal(
        context: Context, @StringRes message: Int, duration: Int,
        icon: Drawable?, withIcon: Boolean
    ): Toast? {
        return custom(
            context,
            context.getString(message),
            icon,
            NORMAL_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    fun normal(
        context: Context, message: CharSequence, duration: Int,
        icon: Drawable?, withIcon: Boolean
    ): Toast? {
        return custom(context, message, icon, NORMAL_COLOR, duration, withIcon, true)
    }

    @CheckResult
    fun warning(context: Context, @StringRes message: Int): Toast? {
        return warning(context, context.getString(message), Toast.LENGTH_SHORT, true)
    }

    @CheckResult
    fun warning(context: Context, message: CharSequence): Toast? {
        return warning(context, message, Toast.LENGTH_SHORT, true)
    }

    @CheckResult
    fun warning(context: Context, @StringRes message: Int, duration: Int): Toast? {
        return warning(context, context.getString(message), duration, true)
    }

    @CheckResult
    fun warning(context: Context, message: CharSequence, duration: Int): Toast? {
        return warning(context, message, duration, true)
    }

    @CheckResult
    fun warning(
        context: Context,
        @StringRes message: Int,
        duration: Int,
        withIcon: Boolean
    ): Toast? {
        return custom(
            context,
            context.getString(message),
            ToastyUtils.getDrawable(context, R.drawable.ic_error_outline_white_48dp),
            WARNING_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    fun warning(context: Context, message: CharSequence, duration: Int, withIcon: Boolean): Toast? {
        return custom(
            context,
            message,
            ToastyUtils.getDrawable(context, R.drawable.ic_error_outline_white_48dp),
            WARNING_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    fun info(context: Context, @StringRes message: Int): Toast? {
        return info(context, context.getString(message), Toast.LENGTH_SHORT, true)
    }

    @CheckResult
    fun info(context: Context, message: CharSequence): Toast? {
        return info(context, message, Toast.LENGTH_SHORT, true)
    }

    @CheckResult
    fun info(context: Context, @StringRes message: Int, duration: Int): Toast? {
        return info(context, context.getString(message), duration, true)
    }

    @CheckResult
    fun info(context: Context, message: CharSequence, duration: Int): Toast? {
        return info(context, message, duration, true)
    }

    @CheckResult
    fun info(context: Context, @StringRes message: Int, duration: Int, withIcon: Boolean): Toast? {
        return custom(
            context,
            context.getString(message),
            ToastyUtils.getDrawable(context, R.drawable.ic_info_outline_white_48dp),
            INFO_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    fun info(context: Context, message: CharSequence, duration: Int, withIcon: Boolean): Toast? {
        return custom(
            context,
            message,
            ToastyUtils.getDrawable(context, R.drawable.ic_info_outline_white_48dp),
            INFO_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    fun success(context: Context, @StringRes message: Int): Toast? {
        return success(context, context.getString(message), Toast.LENGTH_SHORT, true)
    }

    @CheckResult
    fun success(context: Context, message: CharSequence): Toast? {
        return success(context, message, Toast.LENGTH_SHORT, true)
    }

    @CheckResult
    fun success(context: Context, @StringRes message: Int, duration: Int): Toast? {
        return success(context, context.getString(message), duration, true)
    }

    @CheckResult
    fun success(context: Context, message: CharSequence, duration: Int): Toast? {
        return success(context, message, duration, true)
    }

    @CheckResult
    fun success(
        context: Context,
        @StringRes message: Int,
        duration: Int,
        withIcon: Boolean
    ): Toast? {
        return custom(
            context,
            context.getString(message),
            ToastyUtils.getDrawable(context, R.drawable.ic_check_white_48dp),
            SUCCESS_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    fun success(context: Context, message: CharSequence, duration: Int, withIcon: Boolean): Toast? {
        return custom(
            context, message, ToastyUtils.getDrawable(context, R.drawable.ic_check_white_48dp),
            SUCCESS_COLOR, duration, withIcon, true
        )
    }

    @CheckResult
    fun error(context: Context, @StringRes message: Int): Toast? {
        return error(context, context.getString(message), Toast.LENGTH_SHORT, true)
    }

    @CheckResult
    fun error(context: Context, message: CharSequence): Toast? {
        return error(context, message, Toast.LENGTH_SHORT, true)
    }

    @CheckResult
    fun error(context: Context, @StringRes message: Int, duration: Int): Toast? {
        return error(context, context.getString(message), duration, true)
    }

    @CheckResult
    fun error(context: Context, message: CharSequence, duration: Int): Toast? {
        return error(context, message, duration, true)
    }

    @CheckResult
    fun error(context: Context, @StringRes message: Int, duration: Int, withIcon: Boolean): Toast? {
        return custom(
            context,
            context.getString(message),
            ToastyUtils.getDrawable(context, R.drawable.ic_clear_white_48dp),
            ERROR_COLOR,
            duration,
            withIcon,
            true
        )
    }

    @CheckResult
    fun error(context: Context, message: CharSequence, duration: Int, withIcon: Boolean): Toast? {
        return custom(
            context, message, ToastyUtils.getDrawable(context, R.drawable.ic_clear_white_48dp),
            ERROR_COLOR, duration, withIcon, true
        )
    }

    @CheckResult
    fun custom(
        context: Context, @StringRes message: Int, icon: Drawable?,
        duration: Int, withIcon: Boolean
    ): Toast? {
        return custom(context, context.getString(message), icon, -1, duration, withIcon, false)
    }

    @CheckResult
    fun custom(
        context: Context, message: CharSequence, icon: Drawable?,
        duration: Int, withIcon: Boolean
    ): Toast? {
        return custom(context, message, icon, -1, duration, withIcon, false)
    }

    @CheckResult
    fun custom(
        context: Context, @StringRes message: Int, @DrawableRes iconRes: Int,
        @ColorInt tintColor: Int, duration: Int,
        withIcon: Boolean, shouldTint: Boolean
    ): Toast? {
        return custom(
            context, context.getString(message), ToastyUtils.getDrawable(context, iconRes),
            tintColor, duration, withIcon, shouldTint
        )
    }

    @CheckResult
    fun custom(
        context: Context, message: CharSequence, @DrawableRes iconRes: Int,
        @ColorInt tintColor: Int, duration: Int,
        withIcon: Boolean, shouldTint: Boolean
    ): Toast? {
        return custom(
            context, message, ToastyUtils.getDrawable(context, iconRes),
            tintColor, duration, withIcon, shouldTint
        )
    }

    @CheckResult
    fun custom(
        context: Context, @StringRes message: Int, icon: Drawable?,
        @ColorInt tintColor: Int, duration: Int,
        withIcon: Boolean, shouldTint: Boolean
    ): Toast? {
        return custom(
            context, context.getString(message), icon, tintColor, duration,
            withIcon, shouldTint
        )
    }

    @SuppressLint("ShowToast")
    @CheckResult
    fun custom(
        context: Context, message: CharSequence, icon: Drawable?,
        @ColorInt tintColor: Int, duration: Int,
        withIcon: Boolean, shouldTint: Boolean
    ): Toast? {
        var icon = icon
        val currentToast = Toast.makeText(context, "", duration)
        val toastLayout =
            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.toast_layout, null)
        val toastIcon = toastLayout.findViewById<View?>(R.id.toast_icon) as ImageView
        val toastTextView = toastLayout.findViewById<View?>(R.id.toast_text) as TextView
        val drawableFrame: Drawable?
        drawableFrame = if (shouldTint) ToastyUtils.tint9PatchDrawableFrame(
            context,
            tintColor
        ) else ToastyUtils.getDrawable(context, R.drawable.toast_frame)
        ToastyUtils.setBackground(toastLayout, drawableFrame)
        if (withIcon) {
            requireNotNull(icon) { "Avoid passing 'icon' as null if 'withIcon' is set to true" }
            if (tintIcon) icon = ToastyUtils.tintIcon(icon, DEFAULT_TEXT_COLOR)
            ToastyUtils.setBackground(toastIcon, icon)
        } else {
            toastIcon.visibility = View.GONE
        }
        toastTextView.text = message
        toastTextView.setTextColor(DEFAULT_TEXT_COLOR)
        toastTextView.typeface = currentTypeface
        toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())
        currentToast.view = toastLayout
        return currentToast
    }

    class Config private constructor() {
        @ColorInt
        private var DEFAULT_TEXT_COLOR = Toasty.DEFAULT_TEXT_COLOR

        @ColorInt
        private var ERROR_COLOR = Toasty.ERROR_COLOR

        @ColorInt
        private var INFO_COLOR = Toasty.INFO_COLOR

        @ColorInt
        private var SUCCESS_COLOR = Toasty.SUCCESS_COLOR

        @ColorInt
        private var WARNING_COLOR = Toasty.WARNING_COLOR
        private var typeface = currentTypeface
        private var textSize = Toasty.textSize
        private var tintIcon = Toasty.tintIcon
        @CheckResult
        fun setTextColor(@ColorInt textColor: Int): Config? {
            DEFAULT_TEXT_COLOR = textColor
            return this
        }

        @CheckResult
        fun setErrorColor(@ColorInt errorColor: Int): Config? {
            ERROR_COLOR = errorColor
            return this
        }

        @CheckResult
        fun setInfoColor(@ColorInt infoColor: Int): Config? {
            INFO_COLOR = infoColor
            return this
        }

        @CheckResult
        fun setSuccessColor(@ColorInt successColor: Int): Config? {
            SUCCESS_COLOR = successColor
            return this
        }

        @CheckResult
        fun setWarningColor(@ColorInt warningColor: Int): Config? {
            WARNING_COLOR = warningColor
            return this
        }

        @CheckResult
        fun setToastTypeface(typeface: Typeface): Config? {
            this.typeface = typeface
            return this
        }

        @CheckResult
        fun setTextSize(sizeInSp: Int): Config? {
            this.textSize = sizeInSp
            return this
        }

        @CheckResult
        fun tintIcon(tintIcon: Boolean): Config? {
            this.tintIcon = tintIcon
            return this
        }

        fun apply() {
            Toasty.DEFAULT_TEXT_COLOR = DEFAULT_TEXT_COLOR
            Toasty.ERROR_COLOR = ERROR_COLOR
            Toasty.INFO_COLOR = INFO_COLOR
            Toasty.SUCCESS_COLOR = SUCCESS_COLOR
            Toasty.WARNING_COLOR = WARNING_COLOR
            currentTypeface = typeface
            Toasty.textSize = textSize
            Toasty.tintIcon = tintIcon
        }

        companion object {
            @CheckResult
            fun getInstance(): Config? {
                return Config()
            }

            fun reset() {
                DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")
                ERROR_COLOR = Color.parseColor("#D50000")
                INFO_COLOR = Color.parseColor("#3F51B5")
                SUCCESS_COLOR = Color.parseColor("#388E3C")
                WARNING_COLOR = Color.parseColor("#FFA900")
                currentTypeface = LOADED_TOAST_TYPEFACE
                textSize = 16
                tintIcon = true
            }
        }
    }
}