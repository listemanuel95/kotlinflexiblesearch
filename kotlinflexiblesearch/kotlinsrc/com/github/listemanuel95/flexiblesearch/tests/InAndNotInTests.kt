package com.github.listemanuel95.flexiblesearch.tests

import com.github.listemanuel95.flexiblesearch.utils.*
import de.hybris.platform.core.model.product.ProductModel
import de.hybris.platform.core.model.user.CustomerModel
import kotlin.test.Test
import kotlin.test.assertEquals


internal class InAndNotInTests {

    @Test
    fun `In Collection Test`() {
        val uids = listOf("keenreviewer14@hybris.com", "asdqwe@gmail.com", "keenreviewer8@hybris.com")
        val query = buildQuery("${selectFrom(customer::class)} ${where(attr(customer.UID) `in` uids)}")
        assertEquals(3, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "keenreviewer14@hybris.com",
            query.queryParameters["uid1"],
            TestUtils.Messages.PARAM_DOES_NOT_MATCH
        )
        assertEquals(
            "asdqwe@gmail.com",
            query.queryParameters["uid2"],
            TestUtils.Messages.PARAM_DOES_NOT_MATCH
        )
        assertEquals(
            "keenreviewer8@hybris.com",
            query.queryParameters["uid3"],
            TestUtils.Messages.PARAM_DOES_NOT_MATCH
        )
        assertEquals(
            "SELECT {PK} FROM {Customer} WHERE {uid} IN  (?uid1, ?uid2, ?uid3)",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Not In Collection Test`() {
        val uids = listOf("keenreviewer14@hybris.com", "keenreviewer8@hybris.com")
        val query = buildQuery("${selectFrom(customer::class)} ${where(attr(customer.UID) notIn uids)}")
        assertEquals(2, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "keenreviewer14@hybris.com",
            query.queryParameters["uid1"],
            TestUtils.Messages.PARAM_DOES_NOT_MATCH
        )
        assertEquals(
            "keenreviewer8@hybris.com",
            query.queryParameters["uid2"],
            TestUtils.Messages.PARAM_DOES_NOT_MATCH
        )
        assertEquals(
            "SELECT {PK} FROM {Customer} WHERE {uid} NOT IN  (?uid1, ?uid2)",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `In Subquery Test`() {
        val subquery = subquery("${selectFieldsFrom(product::class, field(upper(product.CODE), Long::class.java))}")
        val query = buildQuery {
            """
           ${selectFrom(product::class)} 
           ${where(attr(product.CODE) inSubquery subquery)}
            """
        }

        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product}  WHERE {code} IN ({{SELECT UPPER({code}) FROM {Product}}}) ",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Not in Subquery Test`() {
        val subquery = subquery("${selectFieldsFrom(product::class, field(upper(product.CODE), Long::class.java))}")
        val query = buildQuery {
            """
           ${selectFrom(product::class)} 
           ${where(attr(product.CODE) notInSubquery subquery)}
            """
        }

        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product}  WHERE {code} NOT IN ({{SELECT UPPER({code}) FROM {Product}}}) ",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

}
