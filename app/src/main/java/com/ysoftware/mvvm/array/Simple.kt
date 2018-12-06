package com.ysoftware.mvvm.array

import com.ysoftware.mvvm.single.ViewModel

open class SimpleArrayViewModel<M:Comparable<M>, VM:ViewModel<M>>: ArrayViewModel<M, VM, SimpleQuery>() {

    final override fun fetchData(query: SimpleQuery?, block: (Result<List<M>>) -> Unit) {
        fetchData(block)
    }

    open fun fetchData(block: (Result<List<M>>) -> Unit) {
        throw Exception("override ArrayViewModel.fetchData(_:)")
    }
}

class SimpleQuery: Query {

    override val isPaginationEnabled = false

    override fun resetPosition() {}
}