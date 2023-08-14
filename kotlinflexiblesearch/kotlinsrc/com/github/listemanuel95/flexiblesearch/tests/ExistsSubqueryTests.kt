package com.github.listemanuel95.flexiblesearch.tests

import com.github.listemanuel95.flexiblesearch.utils.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ExistsSubqueryTests {

    @Test
    fun `Tests Exists Subquery`() {
        val query = buildQuery("${selectFrom(product::class)} ${whereExists(commonSubquery)}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE EXISTS ({{SELECT UPPER({code}) FROM {Product}}}) ",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Aliased Tests Exists Subquery`() {
        val p = alias("p")
        val query = buildQuery("${selectFrom(product::class `as` p)} ${whereExists(commonSubquery)}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE EXISTS ({{SELECT UPPER({code}) FROM {Product}}}) ",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Tests Not Exists Subquery`() {
        val query = buildQuery("${selectFrom(product::class)} ${whereNotExists(commonSubquery)}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE NOT EXISTS ({{SELECT UPPER({code}) FROM {Product}}}) ",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Aliased Not Tests Exists Subquery`() {
        val p = alias("p")
        val query = buildQuery("${selectFrom(product::class `as` p)} ${whereNotExists(commonSubquery)}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE NOT EXISTS ({{SELECT UPPER({code}) FROM {Product}}}) ",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    companion object {
        private val commonSubquery = subquery("${selectFieldsFrom(product::class, field(upper(product.CODE)))}")
    }
}
