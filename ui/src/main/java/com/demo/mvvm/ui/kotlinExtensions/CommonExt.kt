package com.demo.mvvm.ui.kotlinExtensions

fun Any?.isNotNull(): Boolean {
    return this != null
}

fun Any?.isNull(): Boolean {
    return this == null
}