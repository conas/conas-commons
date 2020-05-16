package com.conas.commons.utils

interface ValueEnum<T> {
    fun getValue(): T
}

interface ValuesEnum<T> {
    fun getValues(): Array<T>
}
