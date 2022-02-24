package com.kixfobby.notebook.preference

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.preference.DialogPreference
import android.preference.PreferenceManager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class FontSizePreference(context: Context?, attrs: AttributeSet?) :
    DialogPreference(context, attrs) {
    private lateinit var fonts: MutableList<String>
    private var selected = 0
    override fun onPrepareDialogBuilder(builder: AlertDialog.Builder) {
        // Data has changed, notify so UI can be refreshed!
        builder.setTitle("Choose A Font Size")
        builder.setPositiveButton(
            "OK",
            DialogInterface.OnClickListener { dialog, whichButton -> // save the choice in the preferences
                val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
                if (selected == 0) editor.putString(
                    "fontsize",
                    "Extra Small"
                ) else if (selected == 1) editor.putString(
                    "fontsize",
                    "Small"
                ) else if (selected == 2) editor.putString(
                    "fontsize",
                    "Medium"
                ) else if (selected == 3) editor.putString(
                    "fontsize",
                    "Large"
                ) else if (selected == 4) editor.putString("fontsize", "Huge")
                editor.commit()
                notifyChanged()
            })
        builder.setNegativeButton("Cancel", null)

        // load the font names and create the adapter
        val arrayOfFonts = arrayOf("Extra Small", "Small", "Medium", "Large", "Huge")
        fonts = listOf(*arrayOfFonts) as MutableList<String>
        val adapter = FontSizeArrayAdapter(context, android.R.layout.simple_list_item_single_choice, fonts)
        builder.setSingleChoiceItems(
            adapter,
            selected,
            DialogInterface.OnClickListener { dialog, which -> // make sure we know what is selected
                selected = which
            })
    }

    class FontSizeArrayAdapter internal constructor(context: Context, resource: Int, objects: MutableList<String>) : ArrayAdapter<String>(context, resource, objects) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            // get the view that would normally be returned
            val v = super.getView(position, convertView, parent)
            val tv = v as TextView
            val option = tv.text.toString()
            if (option == "Extra Small") tv.textSize =
                16.0f else if (option == "Small") tv.textSize =
                20.0f else if (option == "Medium") tv.textSize =
                24.0f else if (option == "Large") tv.textSize =
                28.0f else if (option == "Huge") tv.textSize = 31.0f
            tv.setPadding(40, 3, 10, 3)
            tv.isAllCaps = true
            tv.setTextColor(Color.BLACK)
            tv.setTypeface(Typeface.DEFAULT_BOLD)
            return v
        } // end getView()
    } // end class FontSizeArrayAdapter

    init {

        // figure out the current size. 
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext())
        val font = sharedPref.getString("fontsize", "Medium")
        if (font == "Extra Small") selected = 0 else if (font == "Small") selected =
            1 else if (font == "Medium") selected = 2 else if (font == "Large") selected =
            3 else if (font == "Huge") selected = 4
    }
} // end class ClearListPreference
