package com.conas.commons.hateoas.pagination

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty


data class PaginationResponse<T> (

    @JsonIgnore
    val data: Collection<T>,

    @JsonProperty
    val totalPages: Int,

    @JsonProperty
    val totalElements: Int,

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val page: Int?,

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val limit: Int?,

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val orderBy: String?,

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val orderDirection: String?

) : HalResourceCollection<T>()


class PaginationResponseBuilder<T>
    private constructor(private val data: Collection<T>) {

    private var totalPages: Int? = null
    private var totalElements: Int? = null
    private var paginationRequest: PaginationRequest? = null

    fun totalPages(totalPages: Int): PaginationResponseBuilder<T> {
        this.totalPages = totalPages
        return this
    }

    fun totalElements(totalElements: Int): PaginationResponseBuilder<T> {
        this.totalElements = totalElements
        return this
    }

    fun pagination(paginationRequest: PaginationRequest): PaginationResponseBuilder<T> {
        this.paginationRequest = paginationRequest
        return this
    }

    fun build(): PaginationResponse<T> {
        return PaginationResponse(
                data,
                totalPages!!,
                totalElements!!,
                paginationRequest?.page,
                paginationRequest?.limit,
                paginationRequest?.orderBy,
                paginationRequest?.orderDirection)
    }

    companion object {
        @JvmStatic
        fun <T> create(data: Collection<T>) : PaginationResponseBuilder<T> {
            return PaginationResponseBuilder(data)
        }
    }
}