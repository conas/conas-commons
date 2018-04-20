package com.conas.commons.utils

interface ValueEnum<T> {
    fun getValue(): T
}

interface ValuesEnum<T> {
    fun getValues(): Array<T>
}

fun <T> getByValue(enumClass: Class<T>, value: T): T? where T : Enum<T>, T : ValueEnum<T> {
    for (valueEnum in enumClass.enumConstants) {
        if (valueEnum.getValue() == value) {
            return valueEnum
        }
    }
    return null
}
