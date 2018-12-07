package com.ysoftware.mvvm.single

interface ViewModelDelegate {

    fun <M:Comparable<M>> didUpdateData(viewModel:ViewModel<M>) {}
}

