package com.ysoftware.mvvm.array

interface Query {

    val isPaginationEnabled:Boolean
        get() = true

    val size:Int
        get() = 20

    fun resetPosition()

    fun advance() {}
}