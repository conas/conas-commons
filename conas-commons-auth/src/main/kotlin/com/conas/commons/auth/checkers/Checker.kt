package com.conas.commons.auth.checkers


interface Checker<T> {

    fun check(obj: T)
}