package com.conas.commons.hateoas

import com.conas.commons.hateoas.pagination.PaginationResponse
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
            val uriTemplate = UriTemplate(path + PAGINATION_TEMPLATE)

            paginationResponse.add(
                Link(uriTemplate.expand(
                    assembleParams(page, limit, orderBy, orderDirection)).toString(),
                                   Link.REL_SELF))

            if (HalPagination.hasPrevPage(paginationResponse)) {
                paginationResponse.add(
                    Link(uriTemplate.expand(
                            assembleParams(page!! - 1, limit, orderBy, orderDirection)).toString(),
                                           Link.REL_PREVIOUS))

            }
            if (HalPagination.hasNextPage(paginationResponse)) {
                paginationResponse.add(
                    Link(uriTemplate.expand(
                        assembleParams(page!! + 1, limit, orderBy, orderDirection)).toString(),
                                       Link.REL_NEXT))
            }

            paginationResponse.embedResource("items", paginationResponse.data)
            return paginationResponse
        }
    }
}
