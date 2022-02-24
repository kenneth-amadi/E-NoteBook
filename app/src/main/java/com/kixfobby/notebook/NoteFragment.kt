package com.kixfobby.notebook

import android.content.Intent
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kixfobby.notebook.adapter.NoteAdapter
import com.kixfobby.notebook.db.DatabaseAccess

class NoteFragment : Fragment() {
    private lateinit var adapter: NoteAdapter
    private lateinit var listView: ListView
    private lateinit var mActionMode: ActionMode
    private lateinit var memo: Memo
    private lateinit var databaseAccess: DatabaseAccess
    private lateinit var overlay: View
    private lateinit var nw: View
    private lateinit var od: View
    private var rotate: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.note_frag, container, false)

        databaseAccess = DatabaseAccess.getInstance(requireActivity())!!
        databaseAccess.open()
        memo = Memo()
        NoteActivity()
        items = databaseAccess.getAllMemos()
        databaseAccess.close()

        view.findViewById<View?>(android.R.id.content)
        overlay = view.findViewById(R.id.overlay)
        val fab: FloatingActionButton? = view.findViewById(R.id.fab)
        val newN: CardView = view.findViewById(R.id.newN)
        val oldN: CardView = view.findViewById(R.id.oldN)
        nw = view.findViewById(R.id.nw)
        od = view.findViewById(R.id.od)
        ViewAnimation.initShowOut(nw)
        ViewAnimation.initShowOut(od)
        overlay.visibility = View.GONE
        newN.setOnClickListener {
            EditActivity.clearCache()
            val intent: Intent = Intent(getActivity(), EditActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        oldN.setOnClickListener { // EditActivity.clearCache();
            val intent: Intent = Intent(getActivity(), EditActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        overlay.setOnClickListener { toggleFabMode(fab!!) }
        fab!!.setOnClickListener { v -> toggleFabMode(v) }
        listView = view.findViewById(R.id.lView)
        adapter = NoteAdapter(getActivity(), items)
        listView.setAdapter(adapter)
        adapter.notifyDataSetChanged()
        implementListViewClickListeners()
        return view
    }

    private fun toggleFabMode(v: View) {
        rotate = ViewAnimation.rotateFab(v, !rotate)
        if (rotate) {
            ViewAnimation.showIn(od)
            ViewAnimation.showIn(nw)
            overlay.setVisibility(View.VISIBLE)
        } else {
            ViewAnimation.showOut(od)
            ViewAnimation.showOut(nw)
            overlay.setVisibility(View.GONE)
        }
    }

    private fun implementListViewClickListeners() {
        listView.setOnItemClickListener { parent, view, position, id ->
            memo = items.get(position)!!
            //If ActionMode not null select item
            if (mActionMode != null) onListItemSelect(position) else {
                EditActivity.Companion.clearCache()
                val intent: Intent? = Intent(getActivity(), EditActivity::class.java)
                intent!!.putExtra("MEMO", memo)
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent)
                requireActivity().finish()
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
        }
        listView.setOnItemLongClickListener { parent, view, position, id -> //Select item on long click
            onListItemSelect(position)
            true
        }
    }

    //List item select method
    private fun onListItemSelect(position: Int) {
        adapter.toggleSelection(position) //Toggle the selection
        val hasCheckedItems: Boolean =
            adapter.getSelectedCount() > 0 //Check if any items are already selected or not
        if (hasCheckedItems && mActionMode == null) // there are some selected items, start the actionMode
            mActionMode = (requireActivity() as AppCompatActivity?)?.startSupportActionMode(
                Toolbar_ActionMode_Callback(requireActivity(), adapter, items, true)
            )!! else if (!hasCheckedItems && mActionMode != null) // there no selected items, finish the actionMode
            mActionMode.finish()
        if (mActionMode != null) {
            if (adapter.getSelectedCount() > 1) {
                mActionMode.setTitle(adapter.getSelectedCount().toString() + " Notes Selected")
                mActionMode.setSubtitle("Confirm before deletion")
            } else {
                mActionMode.setTitle(adapter.getSelectedCount().toString() + " Note Selected")
                mActionMode.setSubtitle("Confirm before deletion")
            }
        }
    }

    //Set action mode null after use
    fun setNullToActionMode() {
        if (mActionMode != null) mActionMode
    }

    //Delete selected rows
    fun deleteRows() {
        val selected: SparseBooleanArray? = adapter.getSelectedIds() //Get selected ids
        for (i in (selected!!.size() - 1) downTo 0) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
                databaseAccess.open()
                databaseAccess.delete(items[selected.keyAt(i)]!!)
                databaseAccess.close()
                items.removeAt(selected.keyAt(i))
                adapter.notifyDataSetChanged() //notify adapter
            }
        }
        Toast.makeText(
            getActivity(),
            selected.size().toString() + " Note deleted!",
            Toast.LENGTH_SHORT
        ).show() //Show Toast
        mActionMode.finish() //Finish action mode after use
    }

    companion object {
        private lateinit var items: MutableList<Memo?>
    }
}