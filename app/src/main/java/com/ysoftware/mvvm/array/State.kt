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
    var errorValue: Throwable? = null

    fun makeReset(): State {
        return initial
    }

    fun makeReady(reachedEnd: Boolean): State {
        val state = ready
        state.reachedEndValue = reachedEnd
        return state
    }

    fun makeLoading(): State {
        return if (this == initial) {
            loading
        }
        else {
            loadingMore
        }
    }

    fun makeError(error: Throwable): State {
        return if (this == loading) {
            val state = State.error
            state.errorValue = error
            state
        }
        else {
            val state = State.paginationError
            state.errorValue = error
            state
        }
    }
}