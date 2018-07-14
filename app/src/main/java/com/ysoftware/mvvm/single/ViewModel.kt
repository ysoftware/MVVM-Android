package com.ysoftware.mvvm.single

open class ViewModel<M : Comparable<M>>(var model: M?) : Comparable<ViewModel<M>> {

    companion object {
        fun <M:Comparable<M>> create(model:M?): ViewModel<M> {
            return ViewModel(model)
        }
    }

    // Public properties

    var delegate: ViewModelDelegate? = null
        set(_) {
            if (model != null) {
                delegate?.didUpdatedata(this)
            }
        }

    // Inner properties

    internal var arrayDelegate: ViewModelDelegate? = null
        set(_) {
            if (model != null) {
                arrayDelegate?.didUpdatedata(this)
            }
        }

    // Public methods

    fun notifyUpdated() {
        delegate?.didUpdatedata(this)
        arrayDelegate?.didUpdatedata(this)
    }

    override fun compareTo(other: ViewModel<M>): Int {
        return other.model?.let { model?.compareTo(it) } ?: 0
    }
}