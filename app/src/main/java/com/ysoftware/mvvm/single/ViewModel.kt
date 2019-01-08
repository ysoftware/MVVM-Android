package com.ysoftware.mvvm.single

open class ViewModel<M : Comparable<M>>(var model: M?) : Comparable<ViewModel<M>> {

    // Public properties

    var delegate: ViewModelDelegate? = null
        set(value) {
            field = value
            if (model != null) {
                delegate?.didUpdateData(this)
            }
        }

    // Inner properties

    var arrayDelegate: ViewModelDelegate? = null
        set(value) {
            field = value
            if (model != null) {
                arrayDelegate?.didUpdateData(this)
            }
        }

    // Public methods

    fun notifyUpdated() {
        delegate?.didUpdateData(this)
        arrayDelegate?.didUpdateData(this)
    }

    override fun compareTo(other: ViewModel<M>): Int {
        return other.model?.let { model?.compareTo(it) } ?: 0
    }
}