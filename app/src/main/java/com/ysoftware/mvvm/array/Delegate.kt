package com.ysoftware.mvvm.array

import com.ysoftware.mvvm.single.ViewModel

interface ArrayViewModelDelegate {

    fun didChangeState(state: State) {}

    fun <M:Comparable<M>, VM: ViewModel<M>, Q:Query>didUpdateData(arrayViewModel: ArrayViewModel<M, VM, Q>,
                                                                  update: Update) {}
}