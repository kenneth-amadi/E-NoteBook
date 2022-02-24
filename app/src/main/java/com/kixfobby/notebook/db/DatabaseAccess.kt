package com.kixfobby.notebook.db

import android.database.sqlite.SQLiteDatabase
import com.kixfobby.notebook.Memo
import kotlin.jvm.Volatile
import kotlin.jvm.Synchronized
import android.content.*
import java.util.*

class DatabaseAccess private constructor(context: Context) {
    private var database: SQLiteDatabase? = null
    private val openHelper: DatabaseOpenHelper = DatabaseOpenHelper(context)
    fun open() {
        database = openHelper.writableDatabase
    }

    fun close() {
        database?.close()
    }

    fun save(memo: Memo?) {
        val values = ContentValues()
        values.put("title", memo?.getTitle())
        values.put("date", memo?.getTime())
        values.put("memo", memo?.getText())
        database?.insert(DatabaseOpenHelper.TABLE, null, values)
    }

    fun update(memo: Memo?) {
        val values = ContentValues()
        values.put("title", memo?.getTitle())
        values.put("date", Date().time)
        values.put("memo", memo?.getText())
        val date = memo?.getTime().toString()
        database?.update(DatabaseOpenHelper.Companion.TABLE, values, "date = ?", arrayOf(date))
    }

    fun delete(memo: Memo?) {
        val date = java.lang.Long.toString(memo!!.getTime())
        database?.delete(DatabaseOpenHelper.TABLE, "date = ?", arrayOf(date))
    }

    fun getAllMemos(): MutableList<Memo?> {
        val memos: MutableList<Memo?> = ArrayList()
        val cursor = database!!.rawQuery("SELECT * From memo ORDER BY date DESC", null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val title = cursor.getString(0)
            val time = cursor.getLong(1)
            val text = cursor.getString(2)
            memos.add(Memo(title, time, text))
            cursor.moveToNext()
        }
        cursor.close()
        return memos
    }

    companion object {
        @Volatile
        private var instance: DatabaseAccess? = null
        @Synchronized
        fun getInstance(context: Context): DatabaseAccess? {
            if (instance == null) {
                instance = DatabaseAccess(context)
            }
            return instance
        }
    }

}