package com.github.listemanuel95.flexiblesearch.tests

import com.github.listemanuel95.flexiblesearch.utils.*
import kotlin.test.Test
import kotlin.test.assertEquals

class GroupByTests {

    @Test
    fun `Simple Group By Test`() {
        val query = buildQuery {
            """${
                selectFieldsFrom(
                    product::class,
                    field(count(product.PK), Long::class.java),
                    field(product.NAME, String::class.java)
                )
            } ${groupBy(product.NAME)}
            """
        }
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            listOf(Long::class.java, String::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT COUNT({pk}), {name} FROM {Product} GROUP BY {name}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Group By Multiple Test`() {
        val query = buildQuery {
            """${
                selectFieldsFrom(
                    product::class,
                    field(count(product.PK), Long::class.java),
                    field(product.NAME, String::class.java)
                )
            } ${groupBy(product.CODE, product.NAME, product.DESCRIPTION)}
            """
        }
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            listOf(Long::class.java, String::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT COUNT({pk}), {name} FROM {Product} GROUP BY {code}, {name}, {description}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Group By With Where`() {
        val query = buildQuery {
            """${
                selectFieldsFrom(
                    product::class,
                    field(count(product.PK), Long::class.java),
                    field(product.NAME, String::class.java)
                )
            } ${where(attr(product.NAME) like "%test%")} ${groupBy(product.NAME)}
            """
        }
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("%test%", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            listOf(Long::class.java, String::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT COUNT({pk}), {name} FROM {Product} WHERE {name} LIKE  ?name1 GROUP BY {name}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

}
