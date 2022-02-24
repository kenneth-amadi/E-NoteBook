package com.kixfobby.notebook.db

import com.kixfobby.notebook.PerformEdit
import java.io.Serializable

class Temp : Serializable {
    lateinit var title: String
    lateinit var text: String
    lateinit var date: String
    lateinit var letter: String

    @Transient
    var perform: PerformEdit? = null
}