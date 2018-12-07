package com.ysoftware.mvvm.array

import com.ysoftware.mvvm.single.ViewModel
import com.ysoftware.mvvm.single.ViewModelDelegate
import java.lang.Math.min

open class ArrayViewModel<M : Comparable<M>, VM : ViewModel<M>, Q : Query> : ViewModelDelegate {

    // Public properties

    var query: Q? = null

    var delegate: ArrayViewModelDelegate? = null
        set(value) {
            field = value
            if (array.isNotEmpty()) {
                delegate?.didChangeState(state)
                delegate?.didUpdateData(this, Update.reload)
            }
        }

    var array: ArrayList<VM> = arrayListOf()
        private set

    var state: State = State.initial
        private set(value) {
            field = value
            delegate?.didChangeState(value)
        }

    val numberOfItems get() = array.size

    // Private properties

    private var shouldClearData = false

    private var loadOperationsCount = 0

    // Public methods for override

    open fun fetchData(query: Q?, block: (Result<List<M>>) -> Unit) {
        throw Exception("override ArrayViewModel.fetchData(_:_:)")
    }

    open fun cancelLoadOperation(): Boolean {
        return false
    }

    // Public methods

    fun loadMore() {
        when (state) {
            State.loading, State.loadingMore -> return
            State.ready -> if (state.reachedEndValue!!) return
            else -> { }
        }

        state = state.makeLoading()

        loadOperationsCount += 1
        if (loadOperationsCount > 1 && cancelLoadOperation()) {
            loadOperationsCount -= 1
        }

        fetchData(query) { result ->
            loadOperationsCount -= 1
            if (loadOperationsCount != 0) {
                return@fetchData
            }

            result.onSuccess { items ->
                val reachedEnd = query == null || !query!!.isPaginationEnabled || items.size < query!!.size
                manageItems(items)
                state = state.makeReady(reachedEnd)
            }.onFailure { error ->
                state = state.makeError(error)
            }
        }
        query?.advance()
    }

    fun reloadData() {
        resetData()
        loadMore()
    }

    fun clearData() {
        resetData()
        manageItems(listOf())
    }

    fun manageItems(newItems: List<M>) {
        if (shouldClearData) {
            array = arrayListOf()
            shouldClearData = false
            delegate?.didUpdateData(this, Update.reload)
        }

        val isFirstLoad = array.isEmpty()
        @Suppress("UNCHECKED_CAST")
        array.addAll(newItems.map { ViewModel.create(it) } as Collection<VM>)
        array.forEach { it.arrayDelegate = this }

        if (isFirstLoad) {
            delegate?.didUpdateData(this, Update.reload)
        }
        else {
            val endIndex = array.size
            val startIndex = endIndex - newItems.size
            val indexes = (startIndex until endIndex).map { it }

            val update = Update.append
            update.indexes = indexes
            delegate?.didUpdateData(this, update)
        }
    }

    // Private methods

    private fun resetData() {
        state = state.makeReset()
        shouldClearData = true
        query?.resetPosition()
    }

    // Operations

    fun append(element: VM) {
        array.add(element)
        element.arrayDelegate = this
        val update = Update.append
        update.indexes = listOf(array.size - 1)
        delegate?.didUpdateData(this, update)
    }

    fun item(index: Int, shouldLoadMore: Boolean = false): VM {
        if (shouldLoadMore && index == array.size - 1) {
            loadMore()
        }
        return array[index]
    }

    fun notifyUpdated(viewModel: VM) {
        val index = array.indexOf(viewModel)
        if (index >= 0) {
            val update = Update.update
            update.indexes = listOf(index)
            delegate?.didUpdateData(this, update)
        }
    }

    fun delete(index: Int) {
        array.removeAt(index)
        val update = Update.delete
        update.indexes = listOf(index)
        delegate?.didUpdateData(this, update)
    }

    fun move(index: Int, newIndex: Int) {
        if (array.size > index && index >= 0) {
            val endIndex = min(newIndex, array.size - 1)
            array.add(endIndex, array.removeAt(index))
            val update = Update.move
            update.startIndex = index
            update.endIndex = endIndex
            delegate?.didUpdateData(this, update)
        }
    }

    // Delegate

    override fun <M : Comparable<M>> didUpdateData(viewModel: ViewModel<M>) {
        @Suppress("UNCHECKED_CAST") notifyUpdated(viewModel as VM)
    }
}