package com.ysoftware.mvvm.array

enum class State : Comparable<State> {

    // Cases

    initial,

    loading,

    error,

    ready,

    loadingMore,

    paginationError;

    // Methods

    var reachedEndValue: Boolean? = null
    var errorValue: Error? = null

    fun makeReset(): State {
        return initial
    }

    fun makeReady(reachedEnd: Boolean): State {
        val state = ready
        state.reachedEndValue = reachedEnd
        return state
    }

    fun makeLoading(): State {
        if (this == initial) {
            return loading
        }
        else {
            return loadingMore
        }
    }

    fun makeError(error: Error): State {
        if (this == loading) {
            val state = State.error
            state.errorValue = error
            return state
        }
        else {
            val state = State.paginationError
            state.errorValue = error
            return state
        }
    }
}