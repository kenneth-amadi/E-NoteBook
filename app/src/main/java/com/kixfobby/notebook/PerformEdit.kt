package com.kixfobby.notebook

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.*

open class PerformEdit(editText: EditText) {
    var index = 0
    var history: Stack<Action> = Stack()
    var historyBack: Stack<Action> = Stack()
    private var editable: Editable
    private val editText: EditText
    private var flag = false
    protected fun onEditableChanged(s: Editable?) {}
    protected open fun onTextChanged(s: Editable?) {}

    private fun clearHistory() {
        history.clear()
        historyBack.clear()
    }

    fun undo() {
        if (history.empty()) return
        flag = true
        val action = history.pop()
        historyBack.push(action)
        if (action.isAdd) {
            editable.delete(action.startCursor, action.startCursor + action.actionTarget!!.length)
            editText.setSelection(action.startCursor, action.startCursor)
        } else {
            editable.insert(action.startCursor, action.actionTarget)
            if (action.endCursor == action.startCursor) {
                editText.setSelection(action.startCursor + action.actionTarget!!.length)
            } else {
                editText.setSelection(action.startCursor, action.endCursor)
            }
        }
        flag = false
        if (!history.empty() && history.peek().index == action.index) {
            undo()
        }
    }

    fun redo() {
        if (historyBack.empty()) return
        flag = true
        val action = historyBack.pop()
        history.push(action)
        if (action.isAdd) {
            editable.insert(action.startCursor, action.actionTarget)
            if (action.endCursor == action.startCursor) {
                editText.setSelection(action.startCursor + action.actionTarget!!.length)
            } else {
                editText.setSelection(action.startCursor, action.endCursor)
            }
        } else {
            editable.delete(action.startCursor, action.startCursor + action.actionTarget!!.length)
            editText.setSelection(action.startCursor, action.startCursor)
        }
        flag = false
        if (!historyBack.empty() && historyBack.peek().index == action.index) redo()
    }

    fun setDefaultText(text: CharSequence?) {
        clearHistory()
        flag = true
        editable.replace(0, editable.length, text)
        flag = false
    }

    private inner class Watcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (flag) return
            val end = start + count
            if (end > start && end <= s!!.length) {
                val charSequence = s.subSequence(start, end)
                if (charSequence.isNotEmpty()) {
                    val action = Action(charSequence, start, false)
                    if (count > 1) {
                        action.setSelectCount(count)
                    } else if (count == 1 && count == after) {
                        action.setSelectCount(count)
                    }
                    history.push(action)
                    historyBack.clear()
                    action.setIndex(++index)
                }
            }
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (flag) return
            val end = start + count
            if (end > start) {
                val charSequence = s!!.subSequence(start, end)
                if (charSequence.isNotEmpty()) {
                    val action = Action(charSequence, start, true)
                    history.push(action)
                    historyBack.clear()
                    if (before > 0) {
                        action.setIndex(index)
                    } else {
                        action.setIndex(++index)
                    }
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {
            if (flag) return
            if (s != editable) {
                editable = s!!
                onEditableChanged(s)
            }
            this@PerformEdit.onTextChanged(s)
        }
    }

    inner class Action(var actionTarget: CharSequence?, var startCursor: Int, add: Boolean) {
        var endCursor: Int = startCursor
        var isAdd: Boolean = add
        var index = 0
        fun setSelectCount(count: Int) {
            endCursor += count
        }

        @JvmName("setIndex1")
        fun setIndex(index: Int) {
            this.index = index
        }

    }

    companion object {
        private fun CheckNull(o: Any?, message: String) {
            checkNotNull(o) { message }
        }
    }

    init {
        CheckNull(editText, "EditText")
        editable = editText.text
        this.editText = editText
        editText.addTextChangedListener(Watcher())
    }
}