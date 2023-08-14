package com.github.listemanuel95.flexiblesearch.tests

import com.github.listemanuel95.flexiblesearch.utils.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ConditionsTests {

    @Test
    fun `Test Equals Condition`() {
        val query = buildQuery("${selectFrom(product::class)} ${where(attr(product.NAME) eq "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} =  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Not Equals Condition`() {
        val query = buildQuery("${selectFrom(product::class)} ${where(attr(product.NAME) neq "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} <>  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Less Than Condition`() {
        val query = buildQuery("${selectFrom(product::class)} ${where(attr(product.NAME) lt "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} <  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Less Than Equals Condition`() {
        val query = buildQuery("${selectFrom(product::class)} ${where(attr(product.NAME) lte "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} <=  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Greater Than Condition`() {
        val query = buildQuery("${selectFrom(product::class)} ${where(attr(product.NAME) gt "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} >  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Greater Than Equals Condition`() {
        val query = buildQuery("${selectFrom(product::class)} ${where(attr(product.NAME) gte "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} >=  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Like Condition`() {
        val query = buildQuery("${selectFrom(product::class)} ${where(attr(product.NAME) like "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} LIKE  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Not Like Condition`() {
        val query = buildQuery("${selectFrom(product::class)} ${where(attr(product.NAME) notLike "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} NOT LIKE  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Between Condition`() {
        val query =
            buildQuery("${selectFrom(product::class)} ${where(attr(product.NAME) between ("name" to "othername"))}")
        assertEquals(2, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals("othername", query.queryParameters["name2"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} BETWEEN  ?name1 AND  ?name2",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Not Between Condition`() {
        val query =
            buildQuery("${selectFrom(product::class)} ${where(attr(product.NAME) notBetween ("name" to "othername"))}")
        assertEquals(2, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals("othername", query.queryParameters["name2"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} NOT BETWEEN  ?name1 AND  ?name2",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Is Null Condition`() {
        val query =
            buildQuery("${selectFrom(product::class)} ${where(attr(product.NAME).isNull())}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} IS NULL",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Is Not Null Condition`() {
        val query =
            buildQuery("${selectFrom(product::class)} ${where(attr(product.NAME).isNotNull())}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} IS NOT NULL",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

}
