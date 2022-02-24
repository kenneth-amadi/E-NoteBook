package com.kixfobby.notebook

import android.content.ActivityNotFoundException
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.preference.PreferenceManager
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import java.util.*

class EditText : AppCompatEditText {
    private var mRect: Rect? = null
    private var lineColor: Paint? = null
    private var marginColor: Paint? = null
    private var baseLine = 0f
    private var lineDis = 0f

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        lineDis = textSize / 6f + 0.5f
        mRect = Rect()
        lineColor = Paint(Paint.ANTI_ALIAS_FLAG)
        //lineColor.setStyle(Paint.Style.STROKE);
        lineColor?.color = 0x60000000
        marginColor = Paint(Paint.ANTI_ALIAS_FLAG)
        marginColor?.color = resources.getColor(R.color.red_900)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPref.getString("mode", "Default Theme")
        val line = sharedPref.getBoolean("line", false)
        if (line) {
            lineColor?.strokeWidth = 0.001f
        } else {
            lineColor?.strokeWidth = 2.0f
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (baseLine == 0f) {
            val string = Objects.requireNonNull(text).toString()
            setText("\n\n\n")
            baseLine = (getLineBounds(1, mRect) - getLineBounds(0, mRect)).toFloat()
            setText(string)
        } else {
            var floatY = getLineBounds(0, mRect).toFloat()
            for (i in 0 until measuredHeight) {
                canvas?.drawLine(
                    mRect?.left!!.toFloat(),
                    floatY + lineDis,
                    mRect?.right!!.toFloat(),
                    floatY + lineDis,
                    lineColor!!
                )
                floatY += baseLine
            }
        }
        canvas?.drawLine(12f, 0f, 12f, measuredHeight.toFloat(), marginColor!!)
        canvas?.save()
        canvas?.translate(7f, 0f)
        super.onDraw(canvas)
        canvas?.restore()
    }

    override fun onTextContextMenuItem(item: Int): Boolean {
        if (item == android.R.id.paste) {
            try {
                val clipboardManager =
                    this.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                //if (Objects.requireNonNull(clipboardManager.getPrimaryClipDescription()).hasMimeType("text/*") && Objects.requireNonNull(clipboardManager.getPrimaryClip()).getItemAt(0).getText().length() + Objects.requireNonNull(this.getText()).length() > 1048576) {
                if (clipboardManager.primaryClip?.getItemAt(0)?.text?.length!! + text?.length!! > 1045678
                ) {
                    Toast.makeText(context, "Note too large", Toast.LENGTH_SHORT).show()
                    return true
                }
            } catch (exception: Exception) {
                Toast.makeText(context, "" + exception, Toast.LENGTH_SHORT).show()
            }
        }
        return super.onTextContextMenuItem(item)
    }

    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean {
        return try {
            super.onTouchEvent(motionEvent)
        } catch (activityNotFoundException: ActivityNotFoundException) {
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
            false
        }
    }
}