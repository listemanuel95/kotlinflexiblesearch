package com.github.listemanuel95.flexiblesearch.tests

import com.github.listemanuel95.flexiblesearch.utils.*
import kotlin.test.Test
import kotlin.test.assertEquals

class AliasedConditionsTests {

    @Test
    fun `Test Equals Condition`() {
        val p = alias("p")
        val query = buildQuery("${selectFrom(product::class `as` p)} ${where(p[product.NAME] eq "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} =  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Not Equals Condition`() {
        val p = alias("p")
        val query = buildQuery("${selectFrom(product::class `as` p)} ${where(p[product.NAME] neq "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} <>  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Less Than Condition`() {
        val p = alias("p")
        val query = buildQuery("${selectFrom(product::class `as` p)} ${where(p[product.NAME] lt "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} <  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Less Than Equals Condition`() {
        val p = alias("p")
        val query = buildQuery("${selectFrom(product::class `as` p)} ${where(p[product.NAME] lte "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} <=  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Greater Than Condition`() {
        val p = alias("p")
        val query = buildQuery("${selectFrom(product::class `as` p)} ${where(p[product.NAME] gt "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} >  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Greater Than Equals Condition`() {
        val p = alias("p")
        val query = buildQuery("${selectFrom(product::class `as` p)} ${where(p[product.NAME] gte "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} >=  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Like Condition`() {
        val p = alias("p")
        val query = buildQuery("${selectFrom(product::class `as` p)} ${where(p[product.NAME] like "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} LIKE  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Not Like Condition`() {
        val p = alias("p")
        val query = buildQuery("${selectFrom(product::class `as` p)} ${where(p[product.NAME] notLike "name")}")
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} NOT LIKE  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Between Condition`() {
        val p = alias("p")
        val query =
            buildQuery("${selectFrom(product::class `as` p)} ${where(p[product.NAME] between ("name" to "othername"))}")
        assertEquals(2, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals("othername", query.queryParameters["name2"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} BETWEEN  ?name1 AND  ?name2",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Not Between Condition`() {
        val p = alias("p")
        val query =
            buildQuery("${selectFrom(product::class `as` p)} ${where(p[product.NAME] notBetween ("name" to "othername"))}")
        assertEquals(2, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals("name", query.queryParameters["name1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals("othername", query.queryParameters["name2"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} NOT BETWEEN  ?name1 AND  ?name2",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Is Null Condition`() {
        val p = alias("p")
        val query =
            buildQuery("${selectFrom(product::class `as` p)} ${where(p[product.NAME].isNull())}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} IS NULL",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Test Is Not Null Condition`() {
        val p = alias("p")
        val query =
            buildQuery("${selectFrom(product::class `as` p)} ${where(p[product.NAME].isNotNull())}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} IS NOT NULL",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

}
