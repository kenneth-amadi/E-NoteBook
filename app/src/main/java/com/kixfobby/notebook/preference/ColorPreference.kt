package com.kixfobby.notebook.preference

import android.annotation.SuppressLint
import com.kixfobby.notebook.R
import android.preference.DialogPreference
import android.preference.PreferenceManager
import android.graphics.drawable.BitmapDrawable
import android.app.AlertDialog
import android.content.*
import android.util.AttributeSet
import android.view.*
import android.widget.*

/* ColorPreference
 * 		Lets you pick a color
 */
class ColorPreference(context: Context?, attrs: AttributeSet?) : DialogPreference(context, attrs) {
    protected var color: Int
    protected var defcolor = 0
    protected var attribute: String?
    override fun onBindView(view: View) {
        super.onBindView(view)

        // Set our custom views inside the layout
        val myView = view.findViewById(R.id.currentcolor) as View
        myView?.setBackgroundColor(color)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onPrepareDialogBuilder(builder: AlertDialog.Builder) {
        // Data has changed, notify so UI can be refreshed!
        builder.setTitle("Choose a color")
        builder.setPositiveButton(
            "Ok",
            DialogInterface.OnClickListener { dialog, whichButton -> // save the color
                val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
                editor.putInt(attribute, color)
                editor.commit()
                notifyChanged()
            })
        builder.setNegativeButton(
            "Cancel",
            DialogInterface.OnClickListener { dialog, whichButton -> // set it back to original
                val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
                color = sharedPref.getInt(attribute, defcolor)
            })

        // setup the view
        val factory = LayoutInflater.from(context)
        val colorView = factory.inflate(R.layout.colorchooser, null)
        val colormap = colorView.findViewById<View?>(R.id.colormap) as ImageView

        // set the background to the current color
        colorView.setBackgroundColor(color)

        // setup the click listener
        colormap.setOnTouchListener { v, event ->
            val bd = colormap.drawable as BitmapDrawable
            val bitmap = bd.bitmap

            // get the color value. 
            // scale the touch location
            var x = ((event.x - 15) * bitmap.width / (colormap.width - 30))
            var y = ((event.y - 15) * bitmap.height / (colormap.height - 30))
            if (x >= bitmap.width) x = (bitmap.width - 1).toFloat()
            if (x < 0) x = 0F
            if (y >= bitmap.height) y = (bitmap.height - 1).toFloat()
            if (y < 0) y = 0F

            // set the color
            color = bitmap.getPixel(x.toInt(), y.toInt())
            colorView.setBackgroundColor(color)
            true
        }
        builder.setView(colorView)
    }

    // This is the constructor called by the inflater
    init {
        attribute = attrs!!.getAttributeValue(1)

        // set the layout so we can see the preview color
        widgetLayoutResource = R.layout.prefcolor

        // figure out what the current color is
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext())
        defcolor = if (attribute == "fontcolor") -0x333334 else -0x1000000
        color = sharedPref.getInt(attribute, defcolor)
    }
} // end class ColorPreference
