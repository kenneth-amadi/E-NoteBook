package com.kixfobby.notebook.preference

import com.kixfobby.notebook.R
import android.preference.PreferenceFragment
import android.os.Bundle

class NoteFrag : PreferenceFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_note)
    }
}