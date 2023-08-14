package com.github.listemanuel95.flexiblesearch.tests

import com.github.listemanuel95.flexiblesearch.utils.*
import kotlin.test.Test
import kotlin.test.assertEquals

class HavingTests {

    @Test
    fun `Having Test`() {
        val query = buildQuery {
            """${
                selectFieldsFrom(
                    product::class,
                    field(count(product.PK), Long::class.java),
                    field(product.NAME, String::class.java)
                )
            } ${groupBy(product.NAME)} ${having(count(product.PK) gt 2)}
        """
        }
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(2, query.queryParameters["pk1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            listOf(Long::class.java, String::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT COUNT({pk}), {name} FROM {Product} GROUP BY {name} HAVING COUNT({pk}) >  ?pk1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

}
