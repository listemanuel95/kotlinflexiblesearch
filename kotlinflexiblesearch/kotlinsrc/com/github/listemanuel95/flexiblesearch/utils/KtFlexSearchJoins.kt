package com.github.listemanuel95.flexiblesearch.utils

import de.hybris.platform.core.model.ItemModel
import kotlin.reflect.KClass

/**
 * Builds a `JOIN` clause representation (as a [KtFlexSearchJoin]).
 * For legibility, I recommend using the [outer] argument as a named parameter.
 *
 * Example use (where `p` is a `Product` alias and `v` is a `VariantProduct` alias):
 * ```
 * join(ProductModel::class `as` p on (p["pk"] to v["baseproduct"]), outer = true)
 * ```
 *
 * Example use (using `alias` instead of [as] -same result-):
 * ```
 * join(ProductModel::class alias p on (p["pk"] to v["baseproduct"]))
 * ```
 *
 * @param[join] The join (as obtained using the [on] infix on a [KtFlexSearchAliasedTable])
 * @param[outer] Optional (false by default). If true, writes the join as an `OUTER JOIN`
 * @see[as]
 * @see[on]
 */
fun join(
    join: KtFlexSearchJoin,
    outer: Boolean = false
): KtFlexSearchJoin {
    return join.also {
        it.outer = outer
    }
}

/**
 * Builds a `LEFT JOIN` clause representation (as a [KtFlexSearchJoin]).
 * For legibility, I recommend using the [outer] argument as a named parameter.
 *
 * Example use (where `p` is a `Product` alias and `v` is a `VariantProduct` alias):
 * ```
 * leftJoin(ProductModel::class `as` p on (p["pk"] to v["baseproduct"]), outer = true)
 * ```
 *
 * Example use (using `alias` instead of [as] -same result-):
 * ```
 * leftJoin(ProductModel::class alias p on (p["pk"] to v["baseproduct"]))
 * ```
 *
 * @param[join] The join (as obtained using the [on] infix on a [KtFlexSearchAliasedTable])
 * @param[outer] Optional (false by default). If true, writes the join as an `OUTER JOIN`
 * @see[as]
 * @see[on]
 */
fun leftJoin(
    join: KtFlexSearchJoin,
    outer: Boolean = false
): KtFlexSearchJoin {
    return join.also {
        it.left = true
        it.outer = outer
    }
}

/**
 * Builds a `RIGHT JOIN` clause representation (as a [KtFlexSearchJoin]).
 * For legibility, I recommend using the [outer] argument as a named parameter.
 *
 * Example use (where `p` is a `Product` alias and `v` is a `VariantProduct` alias):
 * ```
 * rightJoin(ProductModel::class `as` p on (p["pk"] to v["baseproduct"]), outer = true)
 * ```
 *
 * Example use (using `alias` instead of [as] -same result-):
 * ```
 * rightJoin(ProductModel::class alias p on (p["pk"] to v["baseproduct"]))
 * ```
 *
 * @param[join] The join (as obtained using the [on] infix on a [KtFlexSearchAliasedTable])
 * @param[outer] Optional (false by default). If true, writes the join as an `OUTER JOIN`
 * @see[as]
 * @see[on]
 */
fun rightJoin(
    join: KtFlexSearchJoin,
    outer: Boolean = false
): KtFlexSearchJoin {
    return join.also {
        it.right = true
        it.outer = outer
    }
}

/**
 * Returns an `alias` builder object ([KtFlexSearchAliasBuilder]), used to reference attributes.
 *
 * Example use:
 * ```
 * val p = alias("p")
 * val query = buildQuery { "${selectFrom(ProductModel::class `as` p)} ${where(p["code"] isNotNull)}" }
 * ```
 *
 * @param[alias] The table's alias as a [String] (usually one or two letters)
 * @see[selectFrom]
 */
fun alias(alias: String): KtFlexSearchAliasBuilder = KtFlexSearchAliasBuilder(alias)

/**
 * Returns several `alias` builder objects ([KtFlexSearchAliasBuilder]), used to reference attributes.
 *
 * Example use:
 * ```
 * val (p, v) = aliases("p", "v")
 * val query = ${selectFrom(GenericVariantProduct::class `as` v, join(ProductModel::class `as` p on (p["pk"] to v["baseproduct"])))}
 * ```
 *
 * @param[alias] The tables' alias' as a [String] vararg (usually one or two letters each)
 * @see[selectFrom]
 */
fun aliases(vararg alias: String): List<KtFlexSearchAliasBuilder> = alias.map {
    KtFlexSearchAliasBuilder(it)
}

/**
 * Infix function that builds a [KtFlexSearchAliasedTable] based on a [String]. The [KtFlexSearchAliasedTable]
 * is then used with the [on] function to build a join clause ([KtFlexSearchJoin]).
 *
 * Example use:
 * ```
 * join("ProductModel" `as` p on (p["pk"] to v["baseproduct"]))
 * ```
 *
 * @param[aliasBuilder] The [KtFlexSearchAliasBuilder] (obtained by calling [alias] or [aliases])
 * @see[on]
 */
infix fun String.`as`(aliasBuilder: KtFlexSearchAliasBuilder) = KtFlexSearchAliasedTable(this, aliasBuilder)

/**
 * Infix function that builds a [KtFlexSearchAliasedTable] based on a [String]. The [KtFlexSearchAliasedTable]
 * is then used with the [on] function to build a join clause ([KtFlexSearchJoin]).
 *
 * Example use:
 * ```
 * join("ProductModel" alias p on (p["pk"] to v["baseproduct"]))
 * ```
 *
 * @param[aliasBuilder] The [KtFlexSearchAliasBuilder] (obtained by calling [alias] or [aliases])
 * @see[as]
 * @see[on]
 */
infix fun String.alias(aliasBuilder: KtFlexSearchAliasBuilder) = KtFlexSearchAliasedTable(this, aliasBuilder)

/**
 * Infix function that builds a [KtFlexSearchAliasedTable] based on a [KClass]`<out ItemModel>`.
 * The [KtFlexSearchAliasedTable] is then used with the [on] function to build a join clause ([KtFlexSearchJoin]).
 *
 * Example use:
 * ```
 * join(ProductModel::class `as` p on (p["pk"] to v["baseproduct"]))
 * ```
 *
 * @param[aliasBuilder] The [KtFlexSearchAliasBuilder] (obtained by calling [alias] or [aliases])
 * @see[on]
 */
infix fun KClass<out ItemModel>.`as`(aliasBuilder: KtFlexSearchAliasBuilder) =
    KtFlexSearchAliasedTable(getTypeCode(this), aliasBuilder)

/**
 * Infix function that builds a [KtFlexSearchAliasedTable] based on a [KClass]`<out ItemModel>`.
 * The [KtFlexSearchAliasedTable] is then used with the [on] function to build a join clause ([KtFlexSearchJoin]).
 *
 * Example use:
 * ```
 * join(ProductModel::class alias p on (p["pk"] to v["baseproduct"]))
 * ```
 *
 * @param[aliasBuilder] The [KtFlexSearchAliasBuilder] (obtained by calling [alias] or [aliases])
 * @see[alias]
 * @see[aliases]
 * @see[on]
 */
infix fun KClass<out ItemModel>.alias(aliasBuilder: KtFlexSearchAliasBuilder) =
    KtFlexSearchAliasedTable(getTypeCode(this), aliasBuilder)

/**
 * Infix function that builds a [KtFlexSearchJoin] based on a [KtFlexSearchAliasedTable].
 * The [KtFlexSearchAliasedTable] is obtained using either the [as] or `alias` infixes.
 *
 * Example use:
 * ```
 * join(ProductModel::class alias p on (p["pk"] to v["baseproduct"]))
 * ```
 *
 * @param[attrs] A [Pair] of aliased attributes ([KtFlexSearchAliasAttr]),
 * usually obtained from alias builders ([KtFlexSearchAliasBuilder])
 * @see[alias]
 * @see[aliases]
 * @see[on]
 */
infix fun KtFlexSearchAliasedTable.on(attrs: Pair<KtFlexSearchAliasAttr, KtFlexSearchAliasAttr>): KtFlexSearchJoin {
    return KtFlexSearchJoin(this, attrs.first, attrs.second)
}

/**
 * Infix function that adds an alias to a [KtFlexSearchSubQuery].
 *
 * Example use (translates to `SELECT DISTINCT query.PK FROM ({{subquery}})`):
 * ```
 * val subquery = subquery("${selectFrom("Product!")} ${where(attr("code") `in` skuList)}")
 * val query = buildQuery {"${selectFromSubquery(subquery1 `as` "query", distinct = true)}"}
 * ```
 *
 * @param[alias] The query's alias
 */
infix fun KtFlexSearchSubQuery.`as`(alias: String): KtFlexSearchSubQuery = this.also { it.alias = alias }

/**
 * Infix function that adds an alias to a [KtFlexSearchSubQuery].
 *
 * Example use (translates to `SELECT DISTINCT query.PK FROM ({{subquery}})`):
 * ```
 * val subquery = subquery("${selectFrom("Product!")} ${where(attr("code") `in` skuList)}")
 * val query = buildQuery {"${selectFromSubquery(subquery1 alias "query", distinct = true)}"}
 * ```
 *
 * @param[alias] The query's alias
 */
infix fun KtFlexSearchSubQuery.alias(alias: String): KtFlexSearchSubQuery = this.also { it.alias = alias }

/**
 * Operator overload for [KtFlexSearchAliasBuilder] that returns a [KtFlexSearchAttr] when accessed with a [String].
 *
 * Example use (translates to `SELECT {PK} FROM {Product} WHERE {code} = '44123'`):
 * ```
 * val p = alias("p")
 * val code = p[ProductModel.CODE] // or just p["code"]
 * val query = buildQuery {"${selectFrom(ProductModel::class)} ${where(code eq "44123")}"}
 * ```
 *
 * @param[attr] The target attribute as a [String]
 * @see[alias]
 */
operator fun KtFlexSearchAliasBuilder.get(attr: String): KtFlexSearchAliasAttr = KtFlexSearchAliasAttr(this.alias, attr)
