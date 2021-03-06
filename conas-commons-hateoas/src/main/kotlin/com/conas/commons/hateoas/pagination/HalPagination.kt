package com.conas.commons.hateoas.pagination

import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.Link
import org.springframework.hateoas.UriTemplate


private const val PAGINATION_TEMPLATE = "{?page,limit,orderBy,orderDirection}"

class HalPagination {
    companion object {

        @JvmStatic
        fun assembleParams(page: Int?,
                           limit: Int?,
                           orderBy: String?,
                           orderDirection: String?): Map<String, Any> {

            val params = HashMap<String, Any>()
            if (page != null) {
                params["page"] = page
            }
            if (limit != null) {
                params["limit"] = limit
            }
            if (orderBy != null) {
                params["orderBy"] = orderBy
            }
            if (orderDirection != null) {
                params["orderDirection"] = orderDirection
            }
            return params
        }

        @JvmStatic
        fun <T> hasNextPage(paginationResponse: PaginationResponse<T>): Boolean {
            return paginationResponse.page != null
                    && paginationResponse.totalPages > paginationResponse.page
        }

        @JvmStatic
        fun <T> hasPrevPage(paginationResponse: PaginationResponse<T>): Boolean {
            return paginationResponse.page != null
                    && paginationResponse.page > 0
        }

        @JvmStatic
        fun <T> toPagination(path: String, paginationResponse: PaginationResponse<T>): PaginationResponse<T> {
            val (_, _, _, page, limit, orderBy, orderDirection) = paginationResponse

            // NOTE(all) strip trailing slash
            val uriTemplate = UriTemplate.of(path + PAGINATION_TEMPLATE)

            paginationResponse.add(
                Link.of(uriTemplate.expand(
                            assembleParams(page, limit, orderBy, orderDirection)).toString(),
                        IanaLinkRelations.SELF))

            if (hasPrevPage(paginationResponse)) {
                paginationResponse.add(
                    Link.of(uriTemplate.expand(
                                assembleParams(page!! - 1, limit, orderBy, orderDirection)).toString(),
                            IanaLinkRelations.PREVIOUS))

            }
            if (hasNextPage(paginationResponse)) {
                paginationResponse.add(
                    Link.of(uriTemplate.expand(
                                assembleParams(page!! + 1, limit, orderBy, orderDirection)).toString(),
                            IanaLinkRelations.NEXT))
            }

            paginationResponse.embedResource("items", paginationResponse.data)
            return paginationResponse
        }
    }
}
