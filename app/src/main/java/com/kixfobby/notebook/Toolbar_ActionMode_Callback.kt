package com.kixfobby.notebook

import com.kixfobby.notebook.db.DatabaseAccess
import android.util.SparseBooleanArray
import com.kixfobby.notebook.adapter.NoteAdapter
import android.content.*
import android.view.*
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment

class Toolbar_ActionMode_Callback(
    private val context: Context,
    private val listView_adapter: NoteAdapter,
    private val items: MutableList<Memo?>,
    private val isListViewFragment: Boolean
) : ActionMode.Callback {
    private val databaseAccess: DatabaseAccess = DatabaseAccess.getInstance(context)!!
    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.menu_delete, menu) //Inflate the menu over action mode
        return true
    }

    public override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {

        //Sometimes the meu will not be visible so for that we need to set their visibility manually in this method
        //So here show action menu according to SDK Levels
        menu.findItem(R.id.action_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return true
    }

    public override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        val listFragment: Fragment = NoteActivity().getFragment(0)!! //Get list view Fragment
        (listFragment as NoteFragment).deleteRows() //delete selected rows
        val selected: SparseBooleanArray = listView_adapter?.getSelectedIds()!!
        val selectedMessageSize: Int = selected.size()

        //Loop to all selected items
        for (i in (selectedMessageSize - 1) downTo 0) {
            if (selected.valueAt(i)) {
                val model: Memo? = items.get(selected.keyAt(i))
                val title: String? = model?.getTitle()
                val subTitle: String? = model?.getText()
                databaseAccess.open()
                databaseAccess.delete(model)
                databaseAccess.close()
            }
        }
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        listView_adapter.removeSelection() // remove selection
        val listFragment: Fragment? = NoteActivity().getFragment(0) //Get list fragment
        if (listFragment != null) (listFragment as NoteFragment?)!!.setNullToActionMode() //Set action mode null
    }

}