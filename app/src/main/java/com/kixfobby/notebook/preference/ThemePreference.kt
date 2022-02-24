package com.kixfobby.notebook.preference

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.preference.DialogPreference
import android.preference.PreferenceManager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.*

class ThemePreference(context: Context?, attrs: AttributeSet?) : DialogPreference(context, attrs) {
    private lateinit var theme: MutableList<String?>
    private var selected = 0
    private var sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext())
    private var mode: String = sharedPref.getString("mode", "Theme Mode 2").toString()

    override fun onPrepareDialogBuilder(builder: AlertDialog.Builder) {
        builder.setTitle("Select App Theme")
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, whichButton ->
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            when (selected) {
                0 -> editor.putString("mode", "Default Theme")
                1 -> editor.putString("mode", "Theme Mode 1")
                2 -> editor.putString("mode", "Theme Mode 2")
                3 -> editor.putString("mode", "Theme Mode 3")
                4 -> editor.putString("mode", "Theme Mode 4")
                else -> editor.putString("mode", "Theme Mode 5")
            }
            editor.commit()
            notifyChanged()
        })
        builder.setNegativeButton("Cancel") { dialog, whichButton -> }
        val arrayOftheme = arrayOf<String?>("Default Theme", "Theme Mode 1", "Theme Mode 2", "Theme Mode 3", "Theme Mode 4", "Theme Mode 5")
        theme = listOf(*arrayOftheme) as MutableList<String?>
        val adapter =
            ThemeArrayAdapter(context, android.R.layout.simple_list_item_single_choice, theme)
        builder.setSingleChoiceItems(adapter, selected) { dialog, which -> selected = which }
    }

    inner class ThemeArrayAdapter(
        context: Context,
        resource: Int,
        objects: MutableList<String?>
    ) : ArrayAdapter<String?>(context, resource, objects) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            // get the view that would normally be returned
            val v = super.getView(position, convertView, parent)
            val tv = v as TextView
            val option = tv.text.toString()
            when (option) {
                "Default Theme" -> tv.setTextColor(Color.parseColor("#3B546E"))
                "Theme Mode 1" -> tv.setTextColor(Color.parseColor("#2E7D32"))
                "Theme Mode 2" -> tv.setTextColor(Color.parseColor("#004AF2"))
                "Theme Mode 3" -> tv.setTextColor(Color.parseColor("#B90808"))
                "Theme Mode 4" -> tv.setTextColor(Color.parseColor("#F57C00"))
                "Theme Mode 5" -> tv.setTextColor(Color.parseColor("#212121"))
            }

            // general options
            tv.setPadding(40, 3, 10, 3)
            tv.isAllCaps = true
            tv.typeface = Typeface.DEFAULT_BOLD
            return v
        } // end getView()
    } // end class ThemeArrayAdapter

    init {
        selected =
            if (mode == "Theme Mode 1") 1 else if (mode == "Theme Mode 2") 2 else if (mode == "Theme Mode 3") 3 else if (mode == "Theme Mode 4") 4 else if (mode == "Theme Mode 5") 5 else 0
    }
} // end class modeTypePreference
