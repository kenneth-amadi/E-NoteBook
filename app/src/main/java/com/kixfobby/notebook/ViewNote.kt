package com.kixfobby.notebook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.kixfobby.notebook.db.Temp
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.*
import java.io.*

class ViewNote : AppCompatActivity() {
    private lateinit var txt1: TextView
    private lateinit var txt2: TextView
    private lateinit var date: TextView
    private lateinit var image_letter: TextView
    private val image: ImageView? = null
    private lateinit var s: Temp
    private lateinit var d: Temp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        setContentView(R.layout.note_view)
        image_letter = findViewById<View?>(R.id.image_letter) as TextView
        txt1 = findViewById<View?>(R.id.txt_title) as TextView
        date = findViewById<View?>(R.id.date) as TextView
        txt2 = findViewById<View?>(R.id.txt_text) as TextView
        //else txt2 = (TextView) findViewById(R.id.txt_text2);
        image_letter.setOnClickListener(View.OnClickListener { onRefresh() })
        //image = (ImageView) findViewById(R.id.image_shape);
        val bundle = intent.extras
        if (bundle != null) {
            val memo = bundle["MEMO"] as Memo?
            if (memo != null) {
                txt1.text = memo._title
                txt2.text = memo._text
                date.text = memo.date.toString()
                if (memo._title!!.length > 1) image_letter.setText(
                    memo._title!!.substring(0, 2).toUpperCase()
                )
                if (memo._title!!.length <= 1) image_letter.setText(
                    memo._title!!.substring(0, 1).toUpperCase()
                )
                onSerialize()
            }
        }
    }

    private fun onSerialize() {
        s = Temp()
        s.title = txt1.text.toString()
        s.text = txt2.text.toString()
        s.date = date.text.toString()
        s.letter = image_letter.text.toString()
        try {
            val fileOut = FileOutputStream(viewCache)
            val out = ObjectOutputStream(fileOut)
            out.writeObject(s)
            out.close()
            fileOut.close()
            Log.e("Temp Serial", "Serialized data saved")
        } catch (i: IOException) {
            i.printStackTrace()
        }
    }

    private fun onDeserialize() {
        if (viewCache!!.exists()) {
            try {
                val fileIn = FileInputStream(viewCache)
                val `in` = ObjectInputStream(fileIn)
                d = `in`.readObject() as Temp
                `in`.close()
                fileIn.close()
            } catch (i: IOException) {
                i.printStackTrace()
                return
            } catch (c: ClassNotFoundException) {
                Log.e("Temp Deserial", "Temp class not found")
                c.printStackTrace()
                return
            }
            txt1.setText(d.title)
            txt2.setText(d.text)
            date.setText(d.date)
            image_letter.setText(d.letter)
        }
    }

    private fun onRefresh() {
        val intent = Intent(this, ViewNote::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onResume() {
        onDeserialize()
        super.onResume()
    }

    override fun onBackPressed() {
        val intent = Intent(this, NoteActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        super.onBackPressed()
    }

    companion object {
        private val viewCache: File? = File(
            Environment.getExternalStorageDirectory().absolutePath.toString() + "/Android/data/com.kixfobby.myworld/cache/",
            ".note_view.ser"
        )

        fun clearCache() {
            viewCache?.delete()
        }
    }
}