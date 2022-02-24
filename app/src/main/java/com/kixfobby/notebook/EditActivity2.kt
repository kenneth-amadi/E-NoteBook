package com.kixfobby.notebook

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.text.Editable
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kixfobby.notebook.db.DatabaseAccess
import com.kixfobby.notebook.preference.NoteSettings
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*

class EditActivity2 constructor() : AppCompatActivity() {
    private lateinit var etText: EditText
    private lateinit var edt: android.widget.EditText
    private val memo: Memo = Memo()
    private val temp: Memo = Memo()
    private lateinit var mPerformEdit: PerformEdit
    private lateinit var dialogView: View
    private lateinit var inflater: LayoutInflater
    private lateinit var mTitle: String
    private lateinit var noteTitle: String
    private lateinit var date: String
    private lateinit var month: String
    private val allMonths: Array<String?> = arrayOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(getBaseContext())
        val value: Boolean = sharedPref.getBoolean("linewrap", true)
        if (!value) setContentView(R.layout.activity_edit_unwrap) else setContentView(R.layout.activity_edit)
        val toolbar: Toolbar? = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Write Note")
        supportActionBar!!.setSubtitle("Untitled")
        val text: CharSequence? = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
        //boolean readonly = getIntent().getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false);
        etText = findViewById(R.id.etText)
        val linear: LinearLayout? = findViewById(R.id.lin)
        etText.setSelection(etText.getText()!!.length)
        inflater = getLayoutInflater()
        dialogView = inflater.inflate(R.layout.custom_edit_dialog, null)
        edt = dialogView.findViewById(R.id.edit1)
        etText.setText(text)
        etText.setSelection(etText.getText()!!.length)
        toolbar!!.setOnClickListener {
            val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this@EditActivity2)
            inflater = getLayoutInflater()
            dialogView = inflater.inflate(R.layout.custom_edit_dialog, null)
            edt = dialogView.findViewById(R.id.edit1)
            edt.setHint("Enter Note Title")
            if (memo != null) edt.setText(memo.getTitle()) else edt.setHint("Enter Note Title")
            edt.setText(supportActionBar!!.getSubtitle())
            if ((supportActionBar!!.getSubtitle()
                    .toString() == "Untitled")
            ) {
                edt.setText(null)
                edt.setHint("Enter Note Title")
            }
            dialogBuilder.setView(dialogView)
            dialogBuilder.setTitle("Note Title")
            dialogBuilder.setCancelable(false)
            dialogBuilder.setNeutralButton("Cancel") { dialog, whichButton -> }
            dialogBuilder.setPositiveButton("Ok") { dialog, whichButton ->
                noteTitle = edt.text.toString()
                supportActionBar!!.subtitle = noteTitle
            }
            dialogBuilder.create().show()
        }
        mPerformEdit = object : PerformEdit(etText) {
            override fun onTextChanged(s: Editable?) {
                super.onTextChanged(s)
            }
        }
        val font: String? = sharedPref.getString("font", "Serif")
        try {
            if (font != null) {
                when (font) {
                    "a" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/a.ttf")
                    "b" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/b.ttf")
                    "c" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/c.ttf")
                    "d" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/d.ttf")
                    "e" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/e.ttf")
                    "f" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/f.ttf")
                    "g" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/g.ttf")
                    "h" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/h.ttf")
                    "i" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/i.ttf")
                    "j" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/j.ttf")
                    "k" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/k.ttf")
                    "l" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/l.ttf")
                    "m" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/m.ttf")
                    "n" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/n.ttf")
                    "o" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/o.ttf")
                    "p" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/p.ttf")
                    "q" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/q.ttf")
                    "r" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/r.ttf")
                    "s" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/s.ttf")
                    "t" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/t.ttf")
                    "u" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/u.ttf")
                    "v" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/v.ttf")
                    "w" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/w.ttf")
                    "x" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/x.ttf")
                    "y" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/y.ttf")
                    "z" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/z.ttf")
                    "aa" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/aa.ttf")
                    "ab" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/ab.ttf")
                    "ac" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/ac.ttf")
                    "ad" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/ad.ttf")
                    "ae" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/ae.ttf")
                    "af" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/af.ttf")
                    "ag" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/ag.ttf")
                    "ah" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/ah.ttf")
                    "ai" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/ai.ttf")
                    "aj" -> etText.typeface = Typeface.createFromAsset(assets, "fonts/aj.ttf")
                    "ak" -> etText.typeface = Typeface.MONOSPACE
                    "al" -> etText.typeface = Typeface.SANS_SERIF
                    "am" -> etText.typeface = Typeface.SERIF
                    else -> throw IllegalStateException("Unexpected value: $font")
                }
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
        val fontsize: Int = sharedPref.getInt("fontsize", 22)
        if (!(fontsize == 22)) etText.setTextSize(java.lang.Float.valueOf(fontsize.toFloat())) else etText.setTextSize(
            22f
        )
        val bgcolor: Int = sharedPref.getInt("bgcolor", -0x1)
        linear!!.setBackgroundColor(bgcolor)
        val fontcolor: Int = sharedPref.getInt("fontcolor", -0x1000000)
        etText.setTextColor(fontcolor)
        etText.setTextColor(fontcolor)
        linear.setBackgroundColor(bgcolor)
        etText.setLinksClickable(true)
    }

    private fun onSaveClick() {
        if (memo == null) {
            temp.setText(etText.text!!.toString())
            temp.setTitle(supportActionBar!!.subtitle.toString())
            val filename1: String = temp.getTitle() + Random().nextInt(1000000) + 1
            if ((temp == null) || (temp.getText().isEmpty())) {
                Toasty.warning(
                    applicationContext,
                    "Note Empty, Can't Save!",
                    Toast.LENGTH_LONG
                )?.show()
            } else {
                if (temp.getTitle().isEmpty() || (temp.getTitle() == "") || (temp.getTitle() == "Untitled")
                ) {
                    Toasty.warning(
                        applicationContext,
                        "Note Untitled, Can't Save!",
                        Toast.LENGTH_LONG
                    )?.show()
                } else {
                    val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
                    alertDialog.setTitle("Save Note")
                    alertDialog.setMessage("Do you want to Save and Exit Editor?")
                    alertDialog.setCancelable(false)
                        .setPositiveButton("Yes, Done", object : DialogInterface.OnClickListener {
                            var txtUri: Uri? = null
                            private val note: String? = null
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                val databaseAccess: DatabaseAccess? =
                                    DatabaseAccess.getInstance(getBaseContext())
                                databaseAccess?.open()
                                databaseAccess?.save(temp)
                                databaseAccess?.close()
                                try {
                                    date =
                                        DateFormat.format("dd-MM-yyyy", System.currentTimeMillis())
                                            .toString()
                                    val dateNumber: Int =
                                        DateFormat.format("M", System.currentTimeMillis())
                                            .toString().toInt()
                                    month = allMonths[dateNumber - 1].toString()
                                    val root = File(Environment.getExternalStorageDirectory(), "Kixfobby/MyWorld/Notes/$month/$date")
                                    if (!root.exists()) {
                                        root.mkdirs()
                                    }
                                    val filepath = File(root, "$filename1.txt")
                                    val writer = FileWriter(filepath)
                                    writer.append(etText.text!!.toString())
                                    writer.flush()
                                    writer.close()
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                                Toasty.success(
                                    applicationContext,
                                    "Note Created",
                                    Toast.LENGTH_SHORT
                                )?.show()
                                onFinish()
                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
                            }
                        })
                        .setNegativeButton(
                            "Back to Editor"
                        ) { dialog, which -> dialog.cancel() }
                    alertDialog.create().show()
                }
            }
        } else {
            memo.setText(etText.text.toString())
            memo.setTitle(supportActionBar!!.subtitle.toString())
            val filename2: String = memo.getTitle() + "(Updated)" + Random().nextInt(1000000) + 1
            if ((memo == null) || (memo.getText().isEmpty())) {
                Toasty.warning(applicationContext, "Note Empty, Can't Save!", Toast.LENGTH_LONG)?.show()
            } else {
                if (memo.getTitle().isEmpty() || (memo.getTitle() == "")) {
                    Toasty.warning(
                        applicationContext,
                        "Note Untitled, Can't Save!",
                        Toast.LENGTH_LONG
                    )?.show()
                } else {
                    val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
                    alertDialog.setTitle("Save Note")
                    alertDialog.setMessage("Do you want to Update and Exit Editor?")
                    alertDialog.setCancelable(false)
                        .setPositiveButton("Yes, Done") { dialog, which ->
                            val databaseAccess: DatabaseAccess? =
                                DatabaseAccess.getInstance(getBaseContext())
                            databaseAccess?.open()
                            databaseAccess?.update(memo)
                            databaseAccess?.close()
                            try {
                                date = DateFormat.format("dd-MM-yyyy", System.currentTimeMillis()).toString()
                                val dateNumber: Int = DateFormat.format("M", System.currentTimeMillis()).toString().toInt()
                                month = allMonths[dateNumber - 1].toString()
                                val root = File(Environment.getExternalStorageDirectory(), "Kixfobby/MyWorld/Notes/$month/$date") //
                                if (!root.exists()) {
                                    root.mkdirs()
                                }
                                val filepath = File(root, "$filename2.txt")
                                val writer = FileWriter(filepath)
                                writer.append(etText.text!!.toString())
                                writer.flush()
                                writer.close()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                            Toasty.success(
                                applicationContext,
                                "Note Updated",
                                Toast.LENGTH_SHORT
                            )?.show()
                            onFinish()
                        }
                        .setNegativeButton(
                            "Back to Editor"
                        ) { dialog, which -> dialog?.cancel() }
                    alertDialog.create()?.show()
                }
            }
        }
    }

    private fun onFinish() {
        val i = Intent(this, NoteActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val databaseAccess: DatabaseAccess? = DatabaseAccess.getInstance(this)
        databaseAccess?.open()
        when (item.getItemId()) {
            R.id.action_search ->                //onSearchClick();
                return true
            R.id.action_save -> {
                onSaveClick()
                return true
            }
            R.id.action_share -> {
                onShareClick()
                return true
            }
            R.id.action_undo -> {
                mPerformEdit.undo()
                return true
            }
            R.id.action_redo -> {
                mPerformEdit.redo()
                return true
            }
            R.id.action_clear -> {
                etText.setText(null)
                return true
            }
            R.id.action_settings -> {
                val intent = Intent(this, NoteSettings::class.java)
                if (memo != null) {
                    memo.setText(
                        Objects.requireNonNull(Objects.requireNonNull(etText.getText())).toString()
                    )
                    memo.setTitle(supportActionBar!!.subtitle.toString())
                    intent.putExtra("MEMO", memo)
                } else {
                    temp.setText(etText.text.toString())
                    temp.setTitle(supportActionBar!!.subtitle.toString())
                    intent.putExtra("MEMO", temp)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
                return true
            }
            R.id.action_print ->                //onPrintClick();
                return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onShareClick() {
        val subject: String = supportActionBar!!.subtitle.toString()
        val text: String = etText.text!!.toString()
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.setType("text/plain")
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(sharingIntent, "Share Note Via:"))
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        val intent = Intent(this, NoteActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        super.onBackPressed()
    }

    companion object {
        private val tempCache: File? = File(Environment.getExternalStorageDirectory().absolutePath + "/Android/data/com.kixfobby.myworld/cache/", ".note_editor.ser")
    }
}