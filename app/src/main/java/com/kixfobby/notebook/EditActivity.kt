package com.kixfobby.notebook

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.kixfobby.notebook.db.DatabaseAccess
import com.kixfobby.notebook.db.Temp
import com.kixfobby.notebook.preference.NoteSettings
import java.io.*
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*

class EditActivity() : AppCompatActivity() {
    private lateinit var etText: EditText
    private lateinit var scrollView: ScrollView
    private var searchView: SearchView? = null
    private var searchItem: MenuItem? = null
    private lateinit var edt: android.widget.EditText
    private var memo: Memo? = null
    private val temp: Memo = Memo()
    private lateinit var mPerformEdit: PerformEdit
    private lateinit var ser_data: Temp
    private lateinit var des_data: Temp
    private lateinit var dialogView: View
    private lateinit var inflater: LayoutInflater
    private lateinit var mTitle: String
    private lateinit var noteTitle: String
    private lateinit var date: String
    private lateinit var month: String
    private val allMonths: Array<String> = arrayOf(
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(baseContext)
        val value = sharedPref.getBoolean("linewrap", true)
        if (!value) setContentView(R.layout.activity_edit_unwrap) else setContentView(R.layout.activity_edit)
        val toolbar = findViewById<Toolbar?>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Write Note")
        supportActionBar!!.setSubtitle("Untitled")

        etText = findViewById(R.id.etText)
        findViewById<LinearLayout?>(R.id.lin)
        etText.setSelection(etText.text!!.length)
        inflater = this@EditActivity.layoutInflater
        dialogView = inflater.inflate(R.layout.custom_edit_dialog, null)
        edt = dialogView.findViewById(R.id.edit1)
        scrollView = findViewById(R.id.scroll)

        val query = QueryTextListener(this, etText, scrollView)
        query.setListeners(searchView, searchItem)
        toolbar.setOnClickListener {
            val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this@EditActivity)
            inflater = this@EditActivity.layoutInflater
            dialogView = inflater.inflate(R.layout.custom_edit_dialog, null)
            edt = dialogView.findViewById(R.id.edit1)
            edt.setHint("Enter Note Title")
            if (memo != null) edt.setText(memo?.getTitle()) else edt.setHint("Enter Note Title")
            edt.setText(supportActionBar!!.getSubtitle())
            if (supportActionBar!!.getSubtitle().toString() == "Untitled") {
                edt.setText(null)
                edt.setHint("Enter Note Title")
            }
            dialogBuilder.setView(dialogView)
            dialogBuilder.setTitle("Note Title")
            dialogBuilder.setCancelable(false)
            dialogBuilder.setNeutralButton("Cancel", { dialog, whichButton -> })
            dialogBuilder.setPositiveButton("Ok") { dialog, whichButton ->
                noteTitle = edt.text.toString()
                supportActionBar!!.subtitle = noteTitle
            }
            dialogBuilder.create().show()
        }
        val intent = intent
        val action = intent.action
        intent.type
        if ((Intent.ACTION_SEND == action)) {
            clearCache()
            try {
                handleSendText(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    val data = intent.data
                    assert(data != null)
                    val path = data!!.path
                    assert(path != null)
                    val fName: String = path!!.substring(path.lastIndexOf("/") + 1)
                    val `in` = contentResolver.openInputStream(data)
                    val rd: Reader = InputStreamReader(`in`, StandardCharsets.UTF_8)
                    val buffer = CharArray(4096)
                    var len: Int
                    val sb: StringBuilder = StringBuilder()
                    while ((rd.read(buffer).also { len = it }) != -1) {
                        sb.append(buffer, 0, len)
                    }
                    rd.close()
                    `in`!!.close()
                    etText.setText(sb.toString())
                    etText.setTextSize(19f)
                    supportActionBar!!.setSubtitle(fName)
                    edt.setText(mTitle)
                } catch (o: Exception) {
                    o.printStackTrace()
                }
            }
        } else if ((Intent.ACTION_VIEW == action)) {
            clearCache()
            val data = intent.getData()
            assert(data != null)
            val path = data?.getPath()
            assert(path != null)
            val fName: String = path!!.substring(path.lastIndexOf("/") + 1)
            try {
                val url: URL?
                url = URL(data.scheme, data.host, data.path)
                var rd: BufferedReader?
                rd = BufferedReader(InputStreamReader(url.openStream()))
                var line: String?
                while ((rd.readLine().also { line = it }) != null) {
                    etText.append(line)
                    etText.append("\n")
                }
                etText.setTextSize(19f)
                supportActionBar!!.setSubtitle(fName)
                edt.setText(mTitle)
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    val `in` = contentResolver.openInputStream(data)
                    val rd: Reader = InputStreamReader(`in`, StandardCharsets.UTF_8)
                    val buffer = CharArray(4096)
                    var len: Int
                    val sb: StringBuilder = StringBuilder()
                    while ((rd.read(buffer).also { len = it }) != -1) {
                        sb.append(buffer, 0, len)
                    }
                    rd.close()
                    `in`!!.close()
                    etText.setText(sb.toString())
                    etText.setTextSize(19f)
                    supportActionBar!!.setSubtitle(fName)
                    edt.setText(mTitle)
                } catch (o: Exception) {
                    o.printStackTrace()
                }
            }
        }
        mPerformEdit = object : PerformEdit(etText) {
            override fun onTextChanged(s: Editable?) {
                super.onTextChanged(s)
            }
        }
        val bundle = getIntent().extras
        if (bundle != null) {
            memo = (bundle["MEMO"] as Memo?)
            if (memo != null) {
                etText.setText(memo?.getText())
                this.supportActionBar?.subtitle = memo?.getTitle()
            }
        }
        val font = sharedPref.getString("font", "Serif")
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
        val fontsize = sharedPref.getString("fontsize", "22")?.toInt()
        if (fontsize != 22) etText.textSize = fontsize!!.toFloat() else etText.textSize = 22f
        val bgcolor = sharedPref.getInt("bgcolor", -0x1)
        etText.setBackgroundColor(bgcolor)
        val fontcolor = sharedPref.getInt("fontcolor", -0x1000000)
        etText.setTextColor(fontcolor)
        etText.setTextColor(fontcolor)
        etText.setBackgroundColor(bgcolor)
        etText.linksClickable = true
    }

    private fun onSaveClick() {
        memo?.setText(etText.text.toString())
        memo?.setTitle(supportActionBar!!.subtitle.toString())
        val filename2: String = memo?.getTitle() + "(Updated)" + Random().nextInt(1000000) + 1
        if (memo?.getText().toString().isEmpty()) {
            Toasty.warning(applicationContext, "Note Empty, Can't Save!", Toast.LENGTH_LONG)?.show()
        } else {
            if (memo?.getTitle().toString().isEmpty() || (memo?.getTitle() == "")) {
                Toasty.warning(applicationContext, "Note Untitled, Can't Save!", Toast.LENGTH_LONG)?.show()
            } else {
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
                alertDialog.setTitle("Save Note")
                alertDialog.setMessage("Do you want to Update and Exit Editor?")
                alertDialog.setCancelable(false)
                    .setPositiveButton("Yes, Done") { dialog, which ->
                        val databaseAccess: DatabaseAccess? = DatabaseAccess.getInstance(baseContext)
                        databaseAccess?.open()
                        databaseAccess?.update(memo)
                        databaseAccess?.close()
                        try {
                            date = DateFormat.format("dd-MM-yyyy", System.currentTimeMillis()).toString()
                            val dateNumber = DateFormat.format("M", System.currentTimeMillis()).toString().toInt()
                            month = allMonths[dateNumber - 1]
                            val root = File(Environment.getExternalStorageDirectory(), "Kixfobby/MyWorld/Notes/$month/$date") //
                            if (!root.exists()) {
                                root.mkdirs()
                            }
                            val filepath = File(root, "$filename2.txt")
                            val writer = FileWriter(filepath)
                            writer.append(etText.text.toString())
                            writer.flush()
                            writer.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        Toasty.success(applicationContext, "Note Updated", Toast.LENGTH_SHORT)?.show()
                        onFinish()
                    }
                    .setNegativeButton(
                        "Back to Editor"
                    ) { dialog, which -> dialog.cancel() }
                alertDialog.create().show()
            }
        }
    }

    private fun handleSendText(intent: Intent) {
        mTitle = intent.getStringExtra(Intent.EXTRA_SUBJECT).toString()
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (sharedText != null) {
            etText.setText(sharedText)
            supportActionBar!!.subtitle = mTitle
            edt.setText(supportActionBar!!.subtitle)
        }
    }

    private fun onFinish() {
        val i = Intent(this, NoteActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    //Temp Serialization
    private fun onSerialize() {
        ser_data = Temp()
        ser_data.title = supportActionBar!!.subtitle.toString()
        ser_data.text = Objects.requireNonNull(etText.text).toString()
        //ser_data.perform = mPerformEdit;
        try {
            val fileOut = FileOutputStream(tempCache)
            val out = ObjectOutputStream(fileOut)
            out.writeObject(ser_data)
            out.close()
            fileOut.close()
            Log.e("Temp Serial", "Serialized data saved")
            //Toast.makeText(getBaseContext(), "Serialized data saved", Toast.LENGTH_SHORT).show();
        } catch (i: IOException) {
            i.printStackTrace()
            //Toast.makeText(getBaseContext(), "Error reading data", Toast.LENGTH_SHORT).show();
        }
    }

    //Temp Deserialization
    fun onDeserialize() {
        if (tempCache.exists()) {
            try {
                val fileIn = FileInputStream(tempCache)
                val `in` = ObjectInputStream(fileIn)
                des_data = `in`.readObject() as Temp
                `in`.close()
                fileIn.close()
                //Toast.makeText(getBaseContext(), "Deserialized data fetched", Toast.LENGTH_SHORT).show();
            } catch (i: IOException) {
                i.printStackTrace()
                //Toast.makeText(getBaseContext(), "Error writing data", Toast.LENGTH_SHORT).show();
            } catch (c: ClassNotFoundException) {
                Log.e("Temp Deserial", "Temp class not found")
                //Toast.makeText(getBaseContext(), "Temp class not found", Toast.LENGTH_SHORT).show();
                c.printStackTrace()
            }
            memo?.setTitle(des_data.title)
            memo?.setText(des_data.text)
            //mPerformEdit = des_data.perform;
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem?.actionView as SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.imeOptions = EditorInfo.IME_ACTION_GO
        searchView?.setOnQueryTextListener(QueryTextListener(this, etText, scrollView))
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val databaseAccess: DatabaseAccess? = DatabaseAccess.getInstance(this)
        databaseAccess?.open()
        when (item.itemId) {
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
                etText.text = null
                return true
            }
            R.id.action_settings -> {
                val intent = Intent(this, NoteSettings::class.java)
                if (memo != null) {
                    memo!!.setText(etText.text.toString())
                    memo!!.setTitle(supportActionBar?.subtitle.toString())
                    intent.putExtra("MEMO", memo)
                } else {
                    temp.setText(etText.text.toString())
                    temp.setTitle(supportActionBar?.subtitle.toString())
                    intent.putExtra("MEMO", temp)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
                return true
            }
            R.id.action_print ->                 //onPrintClick();
                return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun onShareClick() {
        val subject: String = supportActionBar!!.subtitle.toString()
        val text: String = etText.text.toString()
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.setType("text/plain")
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(sharingIntent, "Share Note Via:"))
    }

    override fun onPause() {
        onSerialize()
        super.onPause()
    }

    override fun onResume() {
        onDeserialize()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        onSerialize()
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        onDeserialize()
        super.onRestoreInstanceState(savedInstanceState, persistentState)
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
        private val tempCache: File = File(
            Environment.getExternalStorageDirectory().absolutePath + "/Android/data/com.kixfobby.myworld/cache/",
            ".note_editor.ser"
        )

        fun clearCache() {
            tempCache.delete()
        }
    }
}