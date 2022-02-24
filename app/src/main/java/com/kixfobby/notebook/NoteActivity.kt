package com.kixfobby.notebook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.kixfobby.notebook.preference.NoteSettings
import androidx.viewpager.widget.ViewPager
import androidx.fragment.app.FragmentPagerAdapter
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.util.ArrayList

class NoteActivity constructor() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_main)
        val toolbar: Toolbar? = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val viewPager: ViewPager? = findViewById(R.id.viewPager)
        setupViewPager(viewPager) //Set up View Pager
    }

    //Setting View Pager
    private fun setupViewPager(viewPager: ViewPager?) {
        adapter = ViewPagerAdapter(getSupportFragmentManager())
        adapter!!.addFrag(NoteFragment(), "ListView")
        // adapter.addFrag(new RecyclerView_Fragment(), "RecyclerView");
        viewPager!!.setAdapter(adapter)
    }

    //Return current fragment on basis of Position
    fun getFragment(pos: Int): Fragment {
        return adapter!!.getItem(pos)
    }

    private fun onViewClick(memo: Memo) {
        ViewNote.clearCache()
        val intent = Intent(this, ViewNote::class.java)
        intent.putExtra("MEMO", memo)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    fun onAddClick() {
        EditActivity.clearCache()
        val intent = Intent(this, EditActivity::class.java)
        startActivity(intent)
        //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    fun onEditClick(memo: Memo?) {
        // EditActivity.clearCache();
        /*Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("MEMO", memo);
        startActivity(intent);*/
        // overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    fun onShareClick(memo: Memo) {
        val subject: String = memo.getTitle().toString()
        val text: String = memo.getText().toString()
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.setType("text/plain")
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(sharingIntent, "Share Note Via:"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_info ->                 //onAddClick();
                return true
            R.id.action_settings -> {
                val intent: Intent? = Intent(this, NoteSettings::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private var adapter: ViewPagerAdapter? = null
    }
}

internal class ViewPagerAdapter constructor(manager: FragmentManager?) :
    FragmentPagerAdapter(manager!!) {
    private val mFragmentList: MutableList<Fragment?>? = ArrayList() //fragment array list
    private val mFragmentTitleList: MutableList<String?> = ArrayList() //title array list
    override fun getItem(position: Int): Fragment {
        return mFragmentList!!.get(position)!!
    }

    override fun getCount(): Int {
        return mFragmentList!!.size
    }

    fun addFrag(fragment: Fragment?, title: String?) {
        mFragmentList!!.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList.get(position)
    }
}