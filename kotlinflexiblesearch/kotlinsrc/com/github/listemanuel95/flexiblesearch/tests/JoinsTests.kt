package com.github.listemanuel95.flexiblesearch.tests

import com.github.listemanuel95.flexiblesearch.utils.*
import kotlin.test.Test
import kotlin.test.assertEquals

class JoinsTests {

    @Test
    fun `Multiple Joins Test`() {
        val (e, o, p, sl2p) = aliases("e", "o", "p", "sl2p")
        val query = buildQuery {
            """
                ${
                selectFieldsFrom(
                    order::class `as` o,
                    listOf(field(e["pk"])),
                    join(orderentry::class `as` e on (e[orderentry.ORDER] to o.pk())),
                    join(product::class `as` p on (e[orderentry.PRODUCT] to p.pk())),
                    join(PRODUCT_STOCK_REL `as` sl2p on (sl2p[STOCK_REL_TARGET] to p.pk()))
                )
            }
            """
        }

        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {e:pk} FROM {Order AS o JOIN OrderEntry AS e ON {e:order} = {o:pk} JOIN Product AS p ON {e:product} = {p:pk} JOIN StockLevelProductRelation AS sl2p ON {sl2p:target} = {p:pk} }",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Multiple Joins Different Order Test`() {
        val (e, o, p, sl2p) = aliases("e", "o", "p", "sl2p")
        val query = buildQuery {
            """
                ${
                selectFieldsFrom(
                    PRODUCT_STOCK_REL `as` sl2p,
                    listOf(field(e["pk"])),
                    join(product::class `as` p on (sl2p[STOCK_REL_TARGET] to p.pk())),
                    join(orderentry::class `as` e on (e[orderentry.PRODUCT] to p.pk())),
                    join(order::class `as` o on (e[orderentry.ORDER] to o.pk())),
                )
            }
            """
        }

        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {e:pk} FROM {StockLevelProductRelation AS sl2p JOIN Product AS p ON {sl2p:target} = {p:pk} JOIN OrderEntry AS e ON {e:product} = {p:pk} JOIN Order AS o ON {e:order} = {o:pk} }",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Simple Right Join Test`() {
        val (e, o) = aliases("e", "o")
        val query = buildQuery {
            """
                ${
                selectFieldsFrom(
                    order::class `as` o,
                    listOf(field(e.pk())), leftJoin(orderentry::class `as` e on (e["order"] to o.pk()))
                )
            }
            """
        }

        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {e:pk} FROM {Order AS o LEFT JOIN OrderEntry AS e ON {e:order} = {o:pk} }",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    companion object {
        const val PRODUCT_STOCK_REL = "StockLevelProductRelation"
        const val STOCK_REL_TARGET = "target"
    }

}
