package com.kixfobby.notebook.preference

import com.kixfobby.notebook.Memo
import com.kixfobby.notebook.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.kixfobby.notebook.EditActivity
import android.view.*
import java.util.*

class NoteSettings : AppCompatActivity() {
    var memo: Memo? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val fragment = NoteFrag()
        fragmentManager.beginTransaction().replace(android.R.id.content, fragment).commit()
        val bundle = intent.extras
        if (bundle != null) {
            memo = bundle["MEMO"] as Memo
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val r = Intent(applicationContext, EditActivity::class.java)
        r.putExtra("MEMO", memo)
        r.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(r)
        finish()
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        super.onBackPressed()
    }
}