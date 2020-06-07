package com.conas.commons.hateoas

import com.conas.commons.hateoas.pagination.HalPagination.Companion.toPagination
import com.conas.commons.hateoas.pagination.PaginationRequest
import com.conas.commons.hateoas.pagination.PaginationResponseBuilder.Companion.create
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [
    HalDefaultConfig::class,
    JacksonConfig::class
])
class TestHalPagination {

    @Autowired
    private val builder: Jackson2ObjectMapperBuilder? = null

    @Test
    fun testPagination() {
        val items: MutableList<TestContainer> = ArrayList()
        val item = TestContainer(VALUE)
        items.add(item)

        val pagination =
            toPagination(PATH,
                create(items)
                        .totalElements(TOTAL_ELEMENTS)
                        .totalPages(TOTAL_PAGES)
                        .pagination(PAGINATION_REQUEST)
                        .build())

        val objectMapper = builder!!.build<ObjectMapper>()
        val result = objectMapper.writeValueAsString(pagination)

        assert(result.contains("next"))
        assert(result.contains("previous"))
        assert(result.contains("\"totalPages\":${TOTAL_PAGES}"))
        assert(result.contains("\"totalElements\":${TOTAL_ELEMENTS}"))
        assert(result.contains("\"orderDirection\":\"${ORDER_DIRECTION}\""))
        assert(result.contains("\"orderBy\":\"${VALUE}\""))
        assert(result.contains("\"items\":[{\"value\":\"${VALUE}\"}]"))
    }

    data class TestContainer(@field:JsonProperty var value: String)

    companion object {
        private const val VALUE = "value"
        private const val PATH = "/items"
        private const val ORDER_DIRECTION = "asc"
        private const val TOTAL_PAGES = 5
        private const val TOTAL_ELEMENTS = 10
        private val PAGINATION_REQUEST = PaginationRequest(2, 2, VALUE, ORDER_DIRECTION)
    }
}