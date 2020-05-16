package com.conas.commons.utils

object EnumUtils {
    fun <T, Y> getByValue(enumClass: Class<T>, value: Y): T? where T : Enum<T>, T : ValueEnum<Y> {
        for (valueEnum in enumClass.enumConstants) {
            if (valueEnum.getValue() == value) {
                return valueEnum
            }
        }
        return null
    }
}
