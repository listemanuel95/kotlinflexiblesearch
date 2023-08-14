package com.github.listemanuel95.flexiblesearch.tests

import com.github.listemanuel95.flexiblesearch.utils.*
import kotlin.test.Test
import kotlin.test.assertEquals

class SQLFunctionsTests {

    @Test
    fun `Count SQL Test`() {
        val query = buildQuery("${selectFieldsFrom(product::class, field(count(PK), Long::class.java))}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            listOf(Long::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT COUNT({pk}) FROM {Product}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Aliased Count SQL Test`() {
        val p = alias("p")
        val query = buildQuery("${selectFieldsFrom(product::class `as` p, field(count(p.pk()), Long::class.java))}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            listOf(Long::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT COUNT({p:pk}) FROM {Product AS p}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Sum SQL Test`() {
        val query = buildQuery("${selectFieldsFrom(product::class, field(sum(PK), Long::class.java))}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            listOf(Long::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT SUM({pk}) FROM {Product}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Aliased Sum SQL Test`() {
        val p = alias("p")
        val query = buildQuery("${selectFieldsFrom(product::class `as` p, field(sum(p.pk()), Long::class.java))}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            listOf(Long::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT SUM({p:pk}) FROM {Product AS p}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Min SQL Test`() {
        val query = buildQuery("${selectFieldsFrom(product::class, field(min(PK), Long::class.java))}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            listOf(Long::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT MIN({pk}) FROM {Product}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Aliased Min SQL Test`() {
        val p = alias("p")
        val query = buildQuery("${selectFieldsFrom(product::class `as` p, field(min(p.pk()), Long::class.java))}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            listOf(Long::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT MIN({p:pk}) FROM {Product AS p}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Max SQL Test`() {
        val query = buildQuery("${selectFieldsFrom(product::class, field(max(PK), Long::class.java))}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            listOf(Long::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT MAX({pk}) FROM {Product}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Aliased Max SQL Test`() {
        val p = alias("p")
        val query = buildQuery("${selectFieldsFrom(product::class `as` p, field(max(p.pk()), Long::class.java))}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            listOf(Long::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT MAX({p:pk}) FROM {Product AS p}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Avg SQL Test`() {
        val query = buildQuery("${selectFieldsFrom(product::class, field(avg(PK), Long::class.java))}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            listOf(Long::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT AVG({pk}) FROM {Product}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Aliased Avg SQL Test`() {
        val p = alias("p")
        val query = buildQuery("${selectFieldsFrom(product::class `as` p, field(avg(p.pk()), Long::class.java))}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            listOf(Long::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT AVG({p:pk}) FROM {Product AS p}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    companion object {
        const val PK = "pk"
    }

}
