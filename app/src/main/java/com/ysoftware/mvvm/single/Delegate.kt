package com.ysoftware.mvvm.single

interface ViewModelDelegate {

    fun <M:Comparable<M>> didUpdatedata(viewModel:ViewModel<M>) {}
}

