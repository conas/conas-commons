package com.conas.commons.hateoas.pagination

import com.conas.commons.hateoas.descriptors.SortDirection


private const val SPLIT_CHAR = ","

data class PaginationRequest(
    val page: Int?,
    val limit: Int?,
    val orderBy: String?,
    val orderDirection: String?
) {
    fun orderDirection(): SortDirection? {
        if (orderDirection == null || orderDirection.isEmpty()) {
            return null
        }
        try {
            return SortDirection.valueOf(orderDirection.toUpperCase())
        } catch (e: Exception) {
            return null
        }
    }

    fun orderProperties(): List<String> {
        return orderBy?.split(SPLIT_CHAR) ?: ArrayList()
    }
}
