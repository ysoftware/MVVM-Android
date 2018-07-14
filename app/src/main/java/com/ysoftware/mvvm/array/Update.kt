package com.ysoftware.mvvm.array

enum class Update {

    reload,

    append,

    delete,

    update,

    move;

    var indexes:List<Int>? = null
    var startIndex:Int? = null
    var endIndex:Int? = null
}