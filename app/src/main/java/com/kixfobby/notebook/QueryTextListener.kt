package com.kixfobby.notebook

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import java.util.regex.Matcher
import java.util.regex.Pattern

class QueryTextListener internal constructor(private val context: Context, private val editText: EditText, private val scrollView: ScrollView) : SearchView.OnQueryTextListener {
    private val UPDATE_DELAY: Int = 100
    private val updateHighlight: Runnable? = null
    private val span: BackgroundColorSpan
    private lateinit var editable: Editable
    private var matcher: Matcher? = null
    private var index: Int = 0
    private var height: Int = 0

    override fun onQueryTextChange(newText: String?): Boolean {
        // Use regex search and spannable for highlighting
        height = scrollView.height
        editable = editText.editableText

        // Reset the index and clear highlighting
        if (newText!!.isEmpty()) {
            index = 0
            editable.removeSpan(span)
            //Toast.makeText(context, "no index", Toast.LENGTH_SHORT).show();
            return false
        }

        // Check pattern
        try {
            val pattern: Pattern =
                Pattern.compile(newText, Pattern.CASE_INSENSITIVE or Pattern.MULTILINE)
            matcher = pattern.matcher(editable)
        } catch (e: Exception) {
            Toast.makeText(context, "Can't match text", Toast.LENGTH_SHORT).show()
            return false
        }

        // Find text
        if (matcher!!.find(index)) {
            // Get index
            index = matcher!!.start()

            // Check layout
            if (editText.getLayout() == null) return false

            // Get text position
            val line: Int = editText.getLayout().getLineForOffset(index)
            val pos: Int = editText.getLayout().getLineBaseline(line)

            // Scroll to it
            scrollView.smoothScrollTo(0, pos - height / 2)

            // Highlight it
            editable.setSpan(
                span, matcher!!.start(), matcher!!.end(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        } else {
            index = 0
            Toast.makeText(context, "Text not found", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    // onQueryTextSubmit
    override fun onQueryTextSubmit(query: String?): Boolean {
        // Find next text
        if (matcher!!.find()) {
            // Get index
            index = matcher!!.start()

            // Get text position
            val line: Int = editText.layout.getLineForOffset(index)
            val pos: Int = editText.layout.getLineBaseline(line)

            // Scroll to it
            scrollView.smoothScrollTo(0, pos - height / 2)

            // Highlight it
            editable.setSpan(
                span, matcher!!.start(), matcher!!.end(),
                Spanned.SPAN_COMPOSING
            )
        } else {
            matcher!!.reset()
            index = 0
            Toast.makeText(context, "Reached end in note!", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    fun setListeners(searchView: SearchView?, searchItem: MenuItem?) {
        if (editText != null) {
            editText.addTextChangedListener(object : TextWatcher {
                // afterTextChanged
                public override fun afterTextChanged(s: Editable?) {
                    if (updateHighlight != null) {
                        editText.removeCallbacks(updateHighlight)
                        editText.postDelayed(updateHighlight, UPDATE_DELAY.toLong())
                    }
                }

                // beforeTextChanged
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    if (searchItem != null &&
                        searchItem.isActionViewExpanded
                    ) {
                        val query: CharSequence? = searchView!!.query
                        editText.postDelayed({
                            if (searchItem.isActionViewExpanded) {
                                if (query != null) searchView.setQuery(query, false)
                            }
                        }, UPDATE_DELAY.toLong())
                    }
                }

                // onTextChanged
                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })
        }
        if (scrollView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) // onScrollChange
                scrollView.setOnScrollChangeListener(object : View.OnScrollChangeListener {
                    override fun onScrollChange(
                        v: View?,
                        x: Int,
                        y: Int,
                        oldX: Int,
                        oldY: Int
                    ) {
                        if (updateHighlight != null) {
                            editText.removeCallbacks(updateHighlight)
                            editText.postDelayed(updateHighlight, UPDATE_DELAY.toLong())
                        }
                    }
                }) else  // onScrollChange
                scrollView.viewTreeObserver.addOnScrollChangedListener {
                    if (updateHighlight != null) {
                        editText.removeCallbacks(updateHighlight)
                        editText.postDelayed(updateHighlight, UPDATE_DELAY.toLong())
                    }
                }
        }
    }

    init {
        span = BackgroundColorSpan(context.resources.getColor(R.color.colorControlActivated))
    }
}