package com.kixfobby.notebook.adapter

import android.content.Context
import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.kixfobby.notebook.Memo
import com.kixfobby.notebook.R

class NoteAdapter(private val context: Context?, private val MemoArrayList: MutableList<Memo?>) :
    BaseAdapter() {
    private var mSelectedItemsIds: SparseBooleanArray?
    override fun getCount(): Int {
        return MemoArrayList?.size!!
    }

    override fun getItem(position: Int): Memo? {
        return MemoArrayList?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val holder: ViewHolder
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.note_item, parent, false)
            holder = ViewHolder()
            holder.title = convertView.findViewById(R.id.title)
            holder.sub_title = convertView.findViewById(R.id.text)
            holder.date = convertView.findViewById(R.id.date)
            convertView.tag = holder
        } else holder = convertView.tag as ViewHolder
        holder.title?.text = MemoArrayList?.get(position)?.getShortTitle()
        holder.sub_title?.text = MemoArrayList?.get(position)?.getShortText()
        holder.date?.text = MemoArrayList?.get(position)?.getDate()

        /* Change background color of the selected items in list view  */
        convertView?.setBackgroundColor(
            if (mSelectedItemsIds?.get(position) == true) convertView.resources.getColor(R.color.yellow_100) else Color.TRANSPARENT)
        return convertView
    }

    private class ViewHolder {
        var title: TextView? = null
        var sub_title: TextView? = null
        var date: TextView? = null //RelativeLayout lyt_checked, lyt_image;
    }

    /***
     * Methods required for do selections, remove selections, etc.
     */
    //Toggle selection methods
    fun toggleSelection(position: Int) {
        selectView(position, !mSelectedItemsIds?.get(position)!!)
    }

    //Remove selected selections
    fun removeSelection() {
        mSelectedItemsIds = SparseBooleanArray()
        notifyDataSetChanged()
    }

    //Put or delete selected position into SparseBooleanArray
    fun selectView(position: Int, value: Boolean) {
        if (value) mSelectedItemsIds?.put(position, value) else mSelectedItemsIds?.delete(position)
        notifyDataSetChanged()
    }

    //Get total selected count
    fun getSelectedCount(): Int {
        return mSelectedItemsIds?.size()!!
    }

    //Return all selected ids
    fun getSelectedIds(): SparseBooleanArray? {
        return mSelectedItemsIds
    }

    init {
        mSelectedItemsIds = SparseBooleanArray()
    }
}