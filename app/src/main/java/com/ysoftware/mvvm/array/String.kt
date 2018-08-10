package com.ysoftware.mvvm.array

import com.ysoftware.mvvm.single.ViewModel

open class StringArrayViewModel(array:List<String>): SimpleArrayViewModel<String, ViewModel<String>>() {

    var temp:List<String>? = null

    init {
        temp = array
        reloadData()
    }

    override fun fetchData(block: (List<String>, Error?) -> Unit) {
        block(temp!!, null)
        temp = null
    }

    // Methods

    fun appendString(model:String) {
        append(ViewModel(model))
    }
}