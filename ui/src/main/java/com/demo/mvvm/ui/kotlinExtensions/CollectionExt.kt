package com.demo.mvvm.ui.kotlinExtensions

fun <E> Collection<E>?.isNullOrEmpty(): Boolean {
    return this == null || this.isEmpty()
}