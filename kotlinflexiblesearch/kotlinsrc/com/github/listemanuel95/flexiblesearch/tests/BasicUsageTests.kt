package com.github.listemanuel95.flexiblesearch.tests

import com.github.listemanuel95.flexiblesearch.utils.*
import de.hybris.platform.catalog.enums.ArticleApprovalStatus
import de.hybris.platform.core.model.c2l.CountryModel
import de.hybris.platform.core.model.c2l.CurrencyModel
import de.hybris.platform.core.model.c2l.RegionModel
import de.hybris.platform.core.model.order.AbstractOrderModel
import de.hybris.platform.core.model.product.ProductModel
import de.hybris.platform.core.model.user.AddressModel
import de.hybris.platform.store.BaseStoreModel
import kotlin.test.Test
import kotlin.test.assertEquals

class BasicUsageTests {

    @Test
    fun `Find approved products`() {
        val query = buildQuery {
            """
                ${selectFrom(product::class)} ${
                where(attr(product.APPROVALSTATUS) eq ArticleApprovalStatus.CHECK)
            }"""
        }
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            ArticleApprovalStatus.CHECK,
            query.queryParameters["approvalStatus1"],
            TestUtils.Messages.PARAM_DOES_NOT_MATCH
        )
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {approvalStatus} =  ?approvalStatus1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `First five distinct product codes`() {
        val p = alias("p")
        val query = buildQuery {
            """
            ${selectFieldsFrom(ProductModel::class `as` p, fieldDistinct(p[ProductModel.CODE], String::class.java))}
        """
        }.also { it.count = 5 }
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(5, query.count, TestUtils.Messages.QUERY_COUNT_DOES_NOT_MATCH)
        assertEquals(
            listOf(String::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT DISTINCT {p:code} FROM {Product AS p}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Find product names in Spanish`() {
        val query = buildQuery {
            """
            ${selectFieldsFrom(product::class, field(loc(product.NAME, "es"), String::class.java))}
            """
        }
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            listOf(String::class.java),
            query.getResultClassList(),
            TestUtils.Messages.SEARCH_RESULTS_DONT_MATCH
        )
        assertEquals(
            "SELECT {name[es]} FROM {Product}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Recently modified points of service ordered by baseStore and creation time`() {
        val (p, a) = aliases("p", "a")
        val selectClause = selectFrom(
            pos::class `as` p,
            leftJoin(address::class `as` a on (p[pos.ADDRESS] to a.pk()), outer = true)
        )
        val query = buildQuery {
            """
            $selectClause
            ${where(p[pos.MODIFIEDTIME] gte "2020-10-01")}
            ${or(a[address.MODIFIEDTIME] gte "2020-10-01")}
            ${order(byDesc(p[pos.BASESTORE]), byAsc(p[pos.CREATIONTIME]))}
        """
        }
        assertEquals(2, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {PointOfService AS p LEFT OUTER JOIN Address AS a ON {p:address} = {a:pk} } WHERE {p:modifiedtime} >=  ?modifiedtime1 OR {a:modifiedtime} >=  ?modifiedtime2 ORDER BY {p:baseStore} DESC, {p:creationtime} ASC",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    // Careful: there are some custom relations and types in this query
    @Test
    fun `Find delivery costs for given delivery mode and region`() {
        // aliases
        val (zdm, zdmv, z2c, z2r, s2d) = aliases("zdm", "zdmv", "z2c", "z2r", "s2d")

        // models
        val (order, region) = Pair(mockedOrder, RegionModel()) // should be received as args

        // joins
        val joins = listOf(
            join(deliveryvalue::class `as` zdmv on (zdmv[deliveryvalue.DELIVERYMODE] to zdm.pk())),
            leftJoin(ZONE_TO_COUNTRY_RELATION `as` z2c on (zdmv[deliveryvalue.ZONE] to z2c["source"])),
            leftJoin(ZONE_TO_REGION_RELATION `as` z2r on (zdmv[deliveryvalue.ZONE] to z2r["source"])),
            join(STORE_TO_DELIVERY_RELATION `as` s2d on (zdmv[deliveryvalue.DELIVERYMODE] to s2d["target"]))
        )

        // conditions
        val currencyCondition = zdmv[deliveryvalue.CURRENCY] eq order.currency
        val zoneCondition = "${
            and(
                z2c["target"] eq order.deliveryAddress.country,
                startBraces = true
            )
        } ${or(z2r["target"] eq region, endBraces = true)}"
        val storeCondition = s2d["source"] eq order.store
        val netCondition = zdm[deliverymode.NET] eq order.net
        val activeCondition = zdm[deliverymode.ACTIVE] eq true

        // query
        val query = buildQuery {
            """
           ${selectFieldsFrom(deliverymode::class `as` zdm, listOf(fieldDistinct(zdm.pk())), *joins.toTypedArray())} 
           ${where(currencyCondition)} $zoneCondition ${and(storeCondition)} 
           ${and(netCondition)} ${and(activeCondition)}
            """
        }
        assertEquals(6, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT DISTINCT {zdm:pk} FROM {ZoneDeliveryMode AS zdm JOIN ZoneDeliveryModeValue AS zdmv ON {zdmv:deliveryMode} = {zdm:pk} LEFT JOIN ZoneCountryRelation AS z2c ON {zdmv:zone} = {z2c:source} LEFT JOIN ZoneRegionRelation AS z2r ON {zdmv:zone} = {z2r:source} JOIN BaseStore2DeliveryModeRel AS s2d ON {zdmv:deliveryMode} = {s2d:target} }  WHERE {zdmv:currency} =  ?currency1 AND ({z2c:target} =  ?target2 OR {z2r:target} =  ?target3) AND {s2d:source} =  ?source4  AND {zdm:net} =  ?net5 AND {zdm:active} =  ?active6",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    companion object {
        const val ZONE_TO_COUNTRY_RELATION = "ZoneCountryRelation"
        const val ZONE_TO_REGION_RELATION = "ZoneToRegionRelation"
        const val STORE_TO_DELIVERY_RELATION = "BaseStore2DeliveryModeRel"

        val mockedOrder: AbstractOrderModel = AbstractOrderModel().apply {
            net = true
            store = BaseStoreModel()
            deliveryAddress = AddressModel().also { it.country = CountryModel() }
            currency = CurrencyModel()
        }
    }

}
