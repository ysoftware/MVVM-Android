package com.ysoftware.mvvm.array

import com.ysoftware.mvvm.single.ViewModel

open class SimpleArrayViewModel<M:Comparable<M>, VM:ViewModel<M>>: ArrayViewModel<M, VM, SimpleQuery>() {

    override final fun fetchData(query: SimpleQuery?, block: (List<M>, Error?) -> Unit) {
        fetchData(block)
    }

    open fun fetchData(block: (List<M>, Error?) -> Unit) {
        throw Exception("override ArrayViewModel.fetchData(_:)")
    }
}

class SimpleQuery: Query {

    override val isPaginationEnabled = false

    override fun resetPosition() {}
}