package com.kixfobby.notebook.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.*

internal class DatabaseOpenHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE, null, VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE memo(title TEXT, date INTEGER PRIMARY KEY, memo TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    companion object {
        const val DATABASE: String = "memos.db"
        const val TABLE: String = "memo"
        const val VERSION = 1
    }
}