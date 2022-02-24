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
import java.util.*

class FontTypePreference(context: Context, attrs: AttributeSet) :
    DialogPreference(context, attrs) {
    private var selected = 0
    override fun onPrepareDialogBuilder(builder: AlertDialog.Builder) {
        builder.setTitle("Select A Font Type")
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, whichButton ->
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            when (selected) {
                0 -> editor.putString("font", "a")
                1 -> editor.putString("font", "b")
                2 -> editor.putString("font", "c")
                3 -> editor.putString(
                    "font",
                    "d"
                )
                4 -> editor.putString(
                    "font",
                    "e"
                )
                5 -> editor.putString(
                    "font",
                    "f"
                )
                6 -> editor.putString(
                    "font",
                    "g"
                )
                7 -> editor.putString(
                    "font",
                    "h"
                )
                8 -> editor.putString(
                    "font",
                    "i"
                )
                9 -> editor.putString(
                    "font",
                    "j"
                )
                10 -> editor.putString(
                    "font",
                    "k"
                )
                11 -> editor.putString(
                    "font",
                    "l"
                )
                12 -> editor.putString(
                    "font",
                    "m"
                )
                13 -> editor.putString(
                    "font",
                    "n"
                )
                14 -> editor.putString(
                    "font",
                    "o"
                )
                15 -> editor.putString(
                    "font",
                    "p"
                )
                16 -> editor.putString(
                    "font",
                    "q"
                )
                17 -> editor.putString(
                    "font",
                    "r"
                )
                18 -> editor.putString(
                    "font",
                    "s"
                )
                19 -> editor.putString(
                    "font",
                    "t"
                )
                20 -> editor.putString(
                    "font",
                    "u"
                )
                21 -> editor.putString(
                    "font",
                    "v"
                )
                22 -> editor.putString(
                    "font",
                    "w"
                )
                23 -> editor.putString(
                    "font",
                    "x"
                )
                24 -> editor.putString(
                    "font",
                    "y"
                )
                25 -> editor.putString(
                    "font",
                    "z"
                )
                26 -> editor.putString(
                    "font",
                    "aa"
                )
                27 -> editor.putString(
                    "font",
                    "ab"
                )
                28 -> editor.putString(
                    "font",
                    "ac"
                )
                29 -> editor.putString(
                    "font",
                    "ad"
                )
                30 -> editor.putString(
                    "font",
                    "ae"
                )
                31 -> editor.putString(
                    "font",
                    "af"
                )
                32 -> editor.putString(
                    "font",
                    "ag"
                )
                33 -> editor.putString(
                    "font",
                    "ah"
                )
                34 -> editor.putString(
                    "font",
                    "ai"
                )
                35 -> editor.putString(
                    "font",
                    "aj"
                )
                36 -> editor.putString(
                    "font",
                    "Monospace"
                )
                37 -> editor.putString(
                    "font",
                    "Serif"
                )
                38 -> editor.putString("font", "Sans Serif")
            }
            editor.apply()
            notifyChanged()
        })
        builder.setNegativeButton(
            "Cancel",
            DialogInterface.OnClickListener { dialog, whichButton -> })
        val arrayOfFonts = arrayOf<String?>(
            "Font 1",
            "Font 2",
            "Font 3",
            "Font 4",
            "Font 5",
            "Font 6",
            "Font 7",
            "Font 8",
            "Font 9",
            "Font 10",
            "Font 11",
            "Font 12",
            "Font 13",
            "Font 14",
            "Font 15",
            "Font 16",
            "Font 17",
            "Font 18",
            "Font 19",
            "Font 20",
            "Font 21",
            "Font 22",
            "Font 23",
            "Font 24",
            "Font 25",
            "Font 26",
            "Font 27",
            "Font 28",
            "Font 29",
            "Font 30",
            "Font 31",
            "Font 32",
            "Font 33",
            "Font 34",
            "Font 35",
            "Font 36",
            "Font 37",
            "Font 38",
            "Font 39"
        )
        val fonts = Arrays.asList(*arrayOfFonts)
        val adapter =
            FontTypeArrayAdapter(context, android.R.layout.simple_list_item_single_choice, fonts)
        builder.setSingleChoiceItems(
            adapter,
            selected,
            DialogInterface.OnClickListener { dialog, which -> selected = which })
    }

    class FontTypeArrayAdapter internal constructor(
        context: Context,
        resource: Int,
        objects: MutableList<String?>
    ) : ArrayAdapter<String?>(context, resource, objects) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val v = super.getView(position, convertView, parent)
            val tv = v as TextView
            val option = tv.text.toString()
            if (option == "Font 1") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets,
                    "fonts/a.ttf"
                )
            ) else if (option == "Font 2") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/b.ttf"
                )
            ) else if (option == "Font 3") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/c.ttf"
                )
            ) else if (option == "Font 4") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/d.ttf"
                )
            ) else if (option == "Font 5") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/e.ttf"
                )
            ) else if (option == "Font 6") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/f.ttf"
                )
            ) else if (option == "Font 7") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/g.ttf"
                )
            ) else if (option == "Font 8") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/h.ttf"
                )
            ) else if (option == "Font 9") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/i.ttf"
                )
            ) else if (option == "Font 10") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/j.ttf"
                )
            ) else if (option == "Font 11") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/k.ttf"
                )
            ) else if (option == "Font 12") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/l.ttf"
                )
            ) else if (option == "Font 13") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/m.ttf"
                )
            ) else if (option == "Font 14") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/n.ttf"
                )
            ) else if (option == "Font 15") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/o.ttf"
                )
            ) else if (option == "Font 16") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/p.ttf"
                )
            ) else if (option == "Font 17") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/q.ttf"
                )
            ) else if (option == "Font 18") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/r.ttf"
                )
            ) else if (option == "Font 19") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/s.ttf"
                )
            ) else if (option == "Font 20") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/t.ttf"
                )
            ) else if (option == "Font 21") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/u.ttf"
                )
            ) else if (option == "Font 22") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/v.ttf"
                )
            ) else if (option == "Font 23") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/w.ttf"
                )
            ) else if (option == "Font 24") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/x.ttf"
                )
            ) else if (option == "Font 25") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/y.ttf"
                )
            ) else if (option == "Font 26") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/z.ttf"
                )
            ) else if (option == "Font 27") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/aa.ttf"
                )
            ) else if (option == "Font 28") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/ab.ttf"
                )
            ) else if (option == "Font 29") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/ac.ttf"
                )
            ) else if (option == "Font 30") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/ad.ttf"
                )
            ) else if (option == "Font 31") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/ae.ttf"
                )
            ) else if (option == "Font 32") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/af.ttf"
                )
            ) else if (option == "Font 33") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/ag.ttf"
                )
            ) else if (option == "Font 34") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/ah.ttf"
                )
            ) else if (option == "Font 35") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/ai.ttf"
                )
            ) else if (option == "Font 36") tv.setTypeface(
                Typeface.createFromAsset(
                    context.assets, "fonts/aj.ttf"
                )
            ) else if (option == "Font 37") tv.setTypeface(Typeface.MONOSPACE) else if (option == "Font 38") tv.setTypeface(
                Typeface.SANS_SERIF
            ) else if (option == "Font 39") tv.setTypeface(Typeface.SERIF)
            tv.setPadding(40, 3, 10, 3)
            tv.isAllCaps = true
            tv.setTextColor(Color.BLACK)
            return v
        } // end getView()
    } // end class FontTypeArrayAdapter

    init {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext())
        val font = sharedPref.getString("font", "Sans Serif")
        if (font == "a") selected = 0 else if (font == "b") selected =
            1 else if (font == "c") selected = 2 else if (font == "d") selected =
            3 else if (font == "e") selected = 4 else if (font == "f") selected =
            5 else if (font == "g") selected = 6 else if (font == "h") selected =
            7 else if (font == "i") selected = 8 else if (font == "j") selected =
            9 else if (font == "k") selected = 10 else if (font == "l") selected =
            11 else if (font == "m") selected = 12 else if (font == "n") selected =
            13 else if (font == "o") selected = 14 else if (font == "p") selected =
            15 else if (font == "q") selected = 16 else if (font == "r") selected =
            17 else if (font == "s") selected = 18 else if (font == "t") selected =
            19 else if (font == "u") selected = 20 else if (font == "v") selected =
            21 else if (font == "w") selected = 22 else if (font == "x") selected =
            23 else if (font == "y") selected = 24 else if (font == "z") selected =
            25 else if (font == "aa") selected = 26 else if (font == "ab") selected =
            27 else if (font == "ac") selected = 28 else if (font == "ad") selected =
            29 else if (font == "ae") selected = 30 else if (font == "af") selected =
            31 else if (font == "ag") selected = 32 else if (font == "ah") selected =
            33 else if (font == "ai") selected = 34 else if (font == "aj") selected =
            35 else if (font == "Monospace") selected = 36 else if (font == "Serif") selected =
            37 else if (font == "Sans Serif") selected = 38
    }
} // end class FontTypePreference
