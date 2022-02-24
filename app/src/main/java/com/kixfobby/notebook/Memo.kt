package com.kixfobby.notebook

import android.annotation.SuppressLint
import android.widget.ImageView
import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Memo : Serializable {

    var image: ImageView? = null
    var color: Int = 0
    var date: Date?
    lateinit var _title: String
    lateinit var _text: String
    private var fullDisplayed: Boolean = false

    constructor() {
        date = Date()
    }

    constructor(title: String, time: Long, text: String) : this() {
        date = Date(time)
        this._text = title
        this._text = text
    }

    fun getDate(): String? {
        return dateFormat!!.format(date)
    }

    fun getTime(): Long {
        return date!!.getTime()
    }

    fun setTime(time: Long) {
        date = Date(time)
    }

    fun setTitle(title: String) {
        this._title = title
    }

    fun getTitle(): String {
        return _title
    }

    fun setText(text: String) {
        this._text = text
    }

    fun getText(): String {
        return _text
    }

    fun getShortTitle(): String {
        val temp = _title.replace("\n".toRegex(), " ")
        return if (temp.length > 80) {
            temp.substring(0, 80) + "..."
        } else {
            temp
        }
    }

    fun getShortText(): String? {
        val temp = _text!!.replace("\n".toRegex(), " ")
        return if (temp.length > 100) {
            temp.substring(0, 100) + "..."
        } else {
            temp
        }
    }

    fun setFullDisplayed(fullDisplayed: Boolean) {
        this.fullDisplayed = fullDisplayed
    }

    fun isFullDisplayed(): Boolean {
        return fullDisplayed
    }

    override fun toString(): String {
        return _text!!
    }

    companion object {
        @SuppressLint("SimpleDateFormat")
        private val dateFormat: DateFormat? = SimpleDateFormat("dd/MM/yyy 'at' hh:mm aaa")
    }
}