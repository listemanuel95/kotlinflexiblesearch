package com.github.listemanuel95.flexiblesearch.tests

import com.github.listemanuel95.flexiblesearch.utils.*
import de.hybris.platform.core.model.product.UnitModel
import kotlin.test.Test
import kotlin.test.assertEquals

class BasicTests {

    @Test
    fun `Simple Query Test`() {
        val query = buildQuery("${selectFrom(product::class)}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Simple Fields Query Test`() {
        val query = buildQuery(
            "${
                selectFieldsFrom(
                    product::class,
                    field(product.CODE, String::class.java),
                    field(product.UNIT, UnitModel::class.java)
                )
            }"
        )
        assertEquals(
            listOf(String::class.java, UnitModel::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {code}, {unit} FROM {Product}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Simple Distinct Query Test`() {
        val query = buildQuery("${selectFrom(product::class, distinct = true)}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT DISTINCT {PK} FROM {Product}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Simple Aliased Query Test`() {
        val p = alias("p")
        val query = buildQuery("${selectFrom(product::class `as` p, distinct = true)}")
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT DISTINCT {p:PK} FROM {Product AS p}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

}
