package com.github.listemanuel95.flexiblesearch.utils

import de.hybris.platform.core.model.ItemModel
import kotlin.reflect.KClass

/**
 * Builds a `SELECT` clause for the given table.
 *
 * Example use (translates to `SELECT {PK} FROM {Product}`):
 * ```
 * val p = alias("p")
 * val query = buildQuery("${selectFrom("Product")}")
 * ```
 *
 * @param[from] The table to `SELECT` from, as a [String]
 * @param[distinct] Optional [Boolean] (defaults to false). If true, builds a `SELECT DISTINCT` instead
 * @see[alias]
 */
fun selectFrom(from: String, distinct: Boolean = false): KtFlexSearchSelect =
    KtFlexSearchSelect(KtFlexSearchAliasedTable(from, null)).also { it.distinct = distinct }

/**
 * Builds a `SELECT` clause for the given table.
 *
 * Example use (translates to `SELECT {PK} FROM {Product}`):
 * ```
 * val p = alias("p")
 * val query = buildQuery("${selectFrom(ProductModel::class)}")
 * ```
 *
 * @param[from] The table to `SELECT` from, as a [KClass]`<out ItemModel>`
 * @param[distinct] Optional [Boolean] (defaults to false). If true, builds a `SELECT DISTINCT` instead
 * @see[alias]
 */
fun selectFrom(from: KClass<out ItemModel>, distinct: Boolean = false): KtFlexSearchSelect =
    KtFlexSearchSelect(KtFlexSearchAliasedTable(getTypeCode(from), null)).also { it.distinct = distinct }

/**
 * Builds a `SELECT` clause for the given table.
 *
 * Example use (translates to `SELECT {p:PK} FROM {Product AS p}`):
 * ```
 * val p = alias("p")
 * val query = buildQuery("${selectFrom(ProductModel::class `as` p)}")
 * ```
 *
 * @param[table] The table to `SELECT` from, as a [KtFlexSearchAliasedTable]
 * @param[distinct] Optional [Boolean] (defaults to false). If true, builds a `SELECT DISTINCT` instead
 * @see[as]
 * @see[alias]
 */
fun selectFrom(table: KtFlexSearchAliasedTable, distinct: Boolean = false): KtFlexSearchSelect =
    KtFlexSearchSelect(table).also { it.distinct = distinct }

/**
 * Builds a `SELECT` clause for the given table, with any number of `JOIN` clauses.
 *
 * Example use (translates to `SELECT {v:PK} FROM {GenericVariantProduct AS v JOIN Product AS p ON {p:pk}={v:baseproduct}}`):
 * ```
 * val (p, v) = aliases("p", "v")
 * val query = buildQuery { """
 * ${selectFrom("GenericVariantProduct" `as` v, join("Product" `as` p on (p["pk"] to v["baseproduct"])))}
 * """ }
 * ```
 *
 * @param[table] The table to `SELECT` from, as a [KtFlexSearchAliasedTable]
 * @param[joins] The `JOIN` clauses to append, as [KtFlexSearchJoin]s
 * @see[as]
 * @see[aliases]
 * @see[join]
 */
fun selectFrom(
    table: KtFlexSearchAliasedTable,
    vararg joins: KtFlexSearchJoin
): KtFlexSearchSelect =
    KtFlexSearchSelect(table, joins = joins.toList())

/**
 * Builds a `SELECT` clause for the given table, with any number of `JOIN` clauses.
 *
 * Example use (translates to `SELECT {v:PK} FROM {GenericVariantProduct AS v JOIN Product AS p ON {p:pk}={v:baseproduct}}`):
 * ```
 * val (p, v) = aliases("p", "v")
 * val query = buildQuery { """
 * ${selectFrom("GenericVariantProduct" `as` v, join("Product" `as` p on (p["pk"] to v["baseproduct"])))}
 * """ }
 * ```
 *
 * @param[table] The table to `SELECT` from, as a [KtFlexSearchAliasedTable]
 * @param[distinct] Optional [Boolean] (defaults to false). If true, builds a `SELECT DISTINCT` instead
 * @param[joins] The `JOIN` clauses to append, as [KtFlexSearchJoin]s
 * @see[as]
 * @see[aliases]
 * @see[join]
 */
fun selectFrom(
    table: KtFlexSearchAliasedTable,
    distinct: Boolean = false,
    vararg joins: KtFlexSearchJoin
): KtFlexSearchSelect =
    KtFlexSearchSelect(table, joins = joins.toList()).also { it.distinct = distinct }

/**
 * Builds a `SELECT` clause for the given table, with the given fields.
 *
 * Example use (translates to `SELECT {code}, {name} FROM {Product}`):
 * ```
 * val query = buildQuery("${selectFrom("Product", field("code", String::class.java), field("name", String::class.java)}")
 * ```
 *
 * In order to search for `DISTINCT` fields, use [fieldDistinct]: `fieldDistinct("code", String::class.java)`
 *
 * @param[from] The table to `SELECT` from, as a [String]
 * @param[fields] The fields to `SELECT`, as [KtFlexSearchField]s
 * @see[field]
 * @see[fieldDistinct]
 */
fun selectFieldsFrom(from: String, vararg fields: KtFlexSearchField): KtFlexSearchSelect = KtFlexSearchSelect(
    KtFlexSearchAliasedTable(from, null), fields = fields.toList()
)

/**
 * Builds a `SELECT` clause for the given table, with the given fields.
 *
 * Example use (translates to `SELECT {code}, {name} FROM {Product}`):
 * ```
 * val query = buildQuery("${selectFrom(ProductModel::class, field("code", String::class.java), field("name", String::class.java)}")
 * ```
 *
 * In order to search for `DISTINCT` fields, use [fieldDistinct]: `fieldDistinct("code", String::class.java)`
 *
 * @param[from] The table to `SELECT` from, as a [String]
 * @param[fields] The fields to `SELECT`, as [KtFlexSearchField]s
 * @see[field]
 * @see[fieldDistinct]
 */
fun selectFieldsFrom(from: KClass<out ItemModel>, vararg fields: KtFlexSearchField): KtFlexSearchSelect =
    KtFlexSearchSelect(
        KtFlexSearchAliasedTable(getTypeCode(from), null), fields = fields.toList()
    )

/**
 * Builds a `SELECT` clause for the given table, with the given fields.
 *
 * Example use (translates to `SELECT {p:code}, {p:name} FROM {Product AS p}`):
 * ```
 * val p = alias("p")
 * val query = buildQuery("${selectFrom(ProductModel::class `as` p, field(p["code"], String::class.java), field(p["name"], String::class.java)}")
 * ```
 *
 * In order to search for `DISTINCT` fields, use [fieldDistinct]: `fieldDistinct("code", String::class.java)`
 *
 * @param[table] The table to `SELECT` from, as a [KtFlexSearchAliasedTable]
 * @param[fields] The fields to `SELECT`, as [KtFlexSearchField]s
 * @see[as]
 * @see[field]
 * @see[fieldDistinct]
 */
fun selectFieldsFrom(table: KtFlexSearchAliasedTable, vararg fields: KtFlexSearchField): KtFlexSearchSelect =
    KtFlexSearchSelect(table, fields = fields.toList())

/**
 * Builds a `SELECT` clause for the given table, with the given fields, and any number of `JOIN` clauses.
 *
 * Example use (translates to
 * `SELECT {p:code}, {p:name} FROM {Product AS p JOIN GenericVariantProduct AS v ON {v:baseproduct}={p:pk}}`):
 * ```
 * val (p, v) = aliases("p", "v")
 * val query = buildQuery { """
 * ${selectFrom(ProductModel::class `as` p,
 *      listOf(field(p["code"], String::class.java), field(p["name"], String::class.java)),
 *      join("GenericVariantProduct" `as` v on (v["baseproduct"] to p["pk"])))}
 * """
 * }
 * ```
 *
 * In order to search for `DISTINCT` fields, use [fieldDistinct]: `fieldDistinct("code", String::class.java)`
 *
 * @param[table] The table to `SELECT` from, as a [KtFlexSearchAliasedTable]
 * @param[fields] The fields to `SELECT`, as [KtFlexSearchField]s
 * @param[joins] The `JOIN` clauses to append, as [KtFlexSearchJoin]s
 * @see[as]
 * @see[field]
 * @see[fieldDistinct]
 * @see[join]
 */
fun selectFieldsFrom(
    table: KtFlexSearchAliasedTable,
    fields: List<KtFlexSearchField>,
    vararg joins: KtFlexSearchJoin
): KtFlexSearchSelect = KtFlexSearchSelect(table, fields, joins.toList())

/**
 * Builds a `SELECT` clause for the given subquery (or inner query), with the given fields.
 *
 * Example use:
 * ```
 * val (p, v) = aliases("p", "v")
 * val skuList = listOf("1099285, 1099413, 110561")
 * val subquery1 = subquery("${selectFrom("Product!")} ${where(attr("code") `in` skuList)}")
 * val subquery2 = subquery(
 * """
 * ${selectFrom("GenericVariantProduct" `as` v, join("Product" `as` p on (p["pk"] to v["baseproduct"])))}
 * ${where(v["code"] `in` skuList)}
 * """
 * )
 * val query = buildQuery("${selectFromSubquery(subquery1 union subquery2 `as` "query", field("code", String::class.java))}")
 * ```
 *
 * In order to search for `DISTINCT` fields, use [fieldDistinct]: `fieldDistinct("code", String::class.java)`
 *
 * @param[subquery] The subquery (inner query) to `SELECT` from, as a [KtFlexSearchSubQuery]
 * @param[fields] The fields to `SELECT`, as [KtFlexSearchField]s
 * @see[as]
 * @see[field]
 * @see[fieldDistinct]
 */
fun selectFromSubquery(subquery: KtFlexSearchSubQuery, vararg fields: KtFlexSearchField): KtFlexSearchSubQuerySelect =
    KtFlexSearchSubQuerySelect(subquery, fields.toList())

/**
 * Builds a `SELECT` clause for the given subquery (or inner query), with the given fields.
 *
 * Example use:
 * ```
 * val (p, v) = aliases("p", "v")
 * val skuList = listOf("1099285, 1099413, 110561")
 * val subquery1 = subquery("${selectFrom("Product!")} ${where(attr("code") `in` skuList)}")
 * val subquery2 = subquery(
 * """
 * ${selectFrom("GenericVariantProduct" `as` v, join("Product" `as` p on (p["pk"] to v["baseproduct"])))}
 * ${where(v["code"] `in` skuList)}
 * """
 * )
 * val query = buildQuery("${selectFromSubquery(subquery1 union subquery2 `as` "query", distinct = true)}")
 * ```
 *
 * In order to search for `DISTINCT` fields, use [fieldDistinct]: `fieldDistinct("code", String::class.java)`
 *
 * @param[subquery] The subquery (inner query) to `SELECT` from, as a [KtFlexSearchSubQuery]
 * @param[distinct] Optional [Boolean] (defaults to false). If true, builds a `SELECT DISTINCT` instead
 * @param[fields] The fields to `SELECT`, as [KtFlexSearchField]s
 * @see[as]
 * @see[field]
 * @see[fieldDistinct]
 */
fun selectFromSubquery(
    subquery: KtFlexSearchSubQuery,
    distinct: Boolean,
    vararg fields: KtFlexSearchField
): KtFlexSearchSubQuerySelect =
    KtFlexSearchSubQuerySelect(subquery, fields.toList()).also { it.distinct = distinct }
