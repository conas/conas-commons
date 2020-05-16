package com.conas.commons.hateoas.pagination


open class ListResponse<T>(items: List<T>) : HalResourceCollection<T>() {

    init {
        this.embedResource("items", items)
    }
}