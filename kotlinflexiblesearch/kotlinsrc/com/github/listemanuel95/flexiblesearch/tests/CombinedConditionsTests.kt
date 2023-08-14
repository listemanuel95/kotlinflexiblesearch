package com.github.listemanuel95.flexiblesearch.tests

import com.github.listemanuel95.flexiblesearch.utils.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals


class CombinedConditionsTests {

    @Test
    fun `Is Not Null and Like`() {
        val query = buildQuery {
            """
               ${selectFrom(product::class)} ${where(attr(product.NAME).isNull())}
               ${or(attr(product.NAME) like "%test%")}
            """
        }
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "%test%",
            query.queryParameters["name1"],
            TestUtils.Messages.PARAM_DOES_NOT_MATCH
        )
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} IS NULL OR {name} LIKE  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Is Not Null and Is Null`() {
        val query = buildQuery {
            """
               ${selectFrom(product::class)} ${where(attr(product.NAME).isNull())}
               ${or(attr(product.DESCRIPTION).isNotNull())}
            """
        }
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} IS NULL OR {description} IS NOT NULL",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Is Null and In Subquery`() {
        val subquery = subquery("${selectFieldsFrom(product::class, field(upper(product.CODE)))}")
        val query = buildQuery {
            """
               ${selectFrom(product::class)} ${where(attr(product.NAME).isNull())}
               ${and(attr(product.CODE) insub subquery)}
            """
        }
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} IS NULL AND {code} IN ({{SELECT UPPER({code}) FROM {Product}}}) ",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Is Null and Exists Subquery`() {
        val subquery = subquery("${selectFieldsFrom(product::class, field(upper(product.CODE)))}")
        val query = buildQuery {
            """
               ${selectFrom(product::class)} ${where(attr(product.NAME).isNull())}
               ${andExists(subquery)}
            """
        }
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} IS NULL AND EXISTS ({{SELECT UPPER({code}) FROM {Product}}}) ",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Is Null and SQL Function`() {
        val query = buildQuery {
            """
               ${selectFrom(product::class)} ${where(attr(product.NAME).isNull())}
               ${and(upper(product.CODE) eq attr(product.CODE))}
            """
        }
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} IS NULL AND UPPER({code}) =  {code}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Is Null and Braces with Subquery`() {
        val subquery = subquery("${selectFieldsFrom(product::class, field(upper(product.CODE)))}")
        val query = buildQuery {
            """
               ${selectFrom(product::class)} ${where(attr(product.NAME).isNull())}
               ${and(attr(product.CODE) insub subquery, startBraces = true)} 
               ${or(attr(product.NAME).isNotNull(), endBraces = true)}
            """
        }
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} IS NULL AND ({code} IN ({{SELECT UPPER({code}) FROM {Product}}})   OR {name} IS NOT NULL)",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Is Null or Between`() {
        val (date1, date2) = Pair(Date(), Date())
        val query = buildQuery {
            """
               ${selectFrom(product::class)} ${where(attr(product.NAME).isNull())}
               ${or(attr(product.CREATIONTIME) between (date1 to date2))}
            """
        }
        assertEquals(2, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(date1, query.queryParameters["creationtime1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(date2, query.queryParameters["creationtime2"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {PK} FROM {Product} WHERE {name} IS NULL OR {creationtime} BETWEEN  ?creationtime1 AND  ?creationtime2",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Aliased Is Not Null and Like`() {
        val p = alias("p")
        val query = buildQuery {
            """
               ${selectFrom(product::class `as` p)} ${where(p[product.NAME].isNull())}
               ${or(p[product.NAME] like "%test%")}
            """
        }
        assertEquals(1, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "%test%",
            query.queryParameters["name1"],
            TestUtils.Messages.PARAM_DOES_NOT_MATCH
        )
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} IS NULL OR {p:name} LIKE  ?name1",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Aliased Is Not Null and Is Null`() {
        val p = alias("p")
        val query = buildQuery {
            """
               ${selectFrom(product::class `as` p)} ${where(p[product.NAME].isNull())}
               ${or(p[product.DESCRIPTION].isNotNull())}
            """
        }
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} IS NULL OR {p:description} IS NOT NULL",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Aliased Is Null and In Subquery`() {
        val p = alias("p")
        val subquery = subquery("${selectFieldsFrom(product::class, field(upper(product.CODE)))}")
        val query = buildQuery {
            """
               ${selectFrom(product::class `as` p)} ${where(p[product.NAME].isNull())}
               ${and(p[product.CODE] insub subquery)}
            """
        }
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} IS NULL AND {p:code} IN ({{SELECT UPPER({code}) FROM {Product}}}) ",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Aliased Is Null and Exists Subquery`() {
        val p = alias("p")
        val subquery = subquery("${selectFieldsFrom(product::class, field(upper(product.CODE)))}")
        val query = buildQuery {
            """
               ${selectFrom(product::class `as` p)} ${where(p[product.NAME].isNull())}
               ${andExists(subquery)}
            """
        }
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} IS NULL AND EXISTS ({{SELECT UPPER({code}) FROM {Product}}}) ",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Aliased Is Null and SQL Function`() {
        val p = alias("p")
        val query = buildQuery {
            """
               ${selectFrom(product::class `as` p)} ${where(p[product.NAME].isNull())}
               ${and(upper(p[product.CODE]) eq p[product.CODE])}
            """
        }
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} IS NULL AND UPPER({p:code}) =  {p:code}",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Aliased Is Null and Braces with Subquery`() {
        val p = alias("p")
        val subquery = subquery("${selectFieldsFrom(product::class, field(upper(product.CODE)))}")
        val query = buildQuery {
            """
               ${selectFrom(product::class `as` p)} ${where(p[product.NAME].isNull())}
               AND (${p[product.CODE] insub subquery} OR ${p[product.NAME].isNotNull()})
            """
        }
        assertEquals(0, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} IS NULL AND ({p:code} IN ({{SELECT UPPER({code}) FROM {Product}}})  OR {p:name} IS NOT NULL)",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

    @Test
    fun `Aliased Is Null or Between`() {
        val p = alias("p")
        val (date1, date2) = Pair(Date(), Date())
        val query = buildQuery {
            """
               ${selectFrom(product::class `as` p)} ${where(p[product.NAME].isNull())}
               ${or(p[product.CREATIONTIME] between (date1 to date2))}
            """
        }
        assertEquals(2, query.queryParameters.size, TestUtils.Messages.PARAMS_DONT_MATCH)
        assertEquals(date1, query.queryParameters["creationtime1"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(date2, query.queryParameters["creationtime2"], TestUtils.Messages.PARAM_DOES_NOT_MATCH)
        assertEquals(
            "SELECT {p:PK} FROM {Product AS p} WHERE {p:name} IS NULL OR {p:creationtime} BETWEEN  ?creationtime1 AND  ?creationtime2",
            query.query,
            TestUtils.Messages.QUERY_DOES_NOT_MATCH
        )
    }

}
