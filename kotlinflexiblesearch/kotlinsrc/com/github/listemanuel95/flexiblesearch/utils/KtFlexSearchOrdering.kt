package com.github.listemanuel95.flexiblesearch.utils


/**
 * Base builder for an `ORDER BY` clause. Must be used along with [byAsc] and/or [byDesc]
 *
 * Example use:
 * ```
 * val p = alias("p")
 * val query = buildQuery {
 * """
 * ${selectFrom("PointOfService" `as` p, leftJoin("Address" `as` a on (p["address"] to a["pk"]), true))}
 * ${where(p["modifiedtime"] gte "2020-10-01")}
 * ${or(a["modifiedtime"] gte "2020-10-01")}
 * ${order(byDesc(p["baseStore"]), byAsc(p["creationtime"]))}
 * """
 * }
 * ```
 *
 * @param[attrs] The attributes to order by. Obtained by calling [byAsc] or [byDesc]
 * @see[selectFrom]
 */
fun order(vararg attrs: KtFlexSearchOrderBy): String {
    val attrsStr: String =
        attrs.toList().fold(StringBuilder("")) { sb, it -> sb.append("$it, ") }.dropLast(2).toString()
    return "$ORDER_BY $attrsStr"
}


/**
 * Builds an attribute to be used in an `ORDER BY ASC` clause. Must be used along with [order]
 *
 * Example use:
 * ```
 * val query = buildQuery {
 * """
 * ${selectFrom(ProductModel::class)}
 * ${where(attr(["modifiedtime"]) gte "2020-10-01")}
 * ${order(byAsc("creationtime"))}
 * """
 * }
 * ```
 *
 * @param[attr] The attribute to order by as a [String]
 * @see[selectFrom]
 */
fun byAsc(attr: String): KtFlexSearchOrderBy = by(attr, OrderOp.ASC)

/**
 * Builds an attribute to be used in an `ORDER BY ASC` clause. Must be used along with [order]
 *
 * Example use:
 * ```
 * val p = alias("p")
 * val query = buildQuery {
 * """
 * ${selectFrom("PointOfService" `as` p, leftJoin("Address" `as` a on (p["address"] to a["pk"]), true))}
 * ${where(p["modifiedtime"] gte "2020-10-01")}
 * ${or(a["modifiedtime"] gte "2020-10-01")}
 * ${order(byDesc(p["baseStore"]), byAsc(p["creationtime"]))}
 * """
 * }
 * ```
 *
 * @param[attr] The attribute to order by as a [KtFlexSearchAttr]
 * @see[selectFrom]
 */
fun byAsc(attr: KtFlexSearchAttr): KtFlexSearchOrderBy = by(attr, OrderOp.ASC)

/**
 * Builds an attribute to be used in an `ORDER BY DESC` clause. Must be used along with [order]
 *
 * Example use:
 * ```
 * val query = buildQuery {
 * """
 * ${selectFrom(ProductModel::class)}
 * ${where(attr(["modifiedtime"]) gte "2020-10-01")}
 * ${order(byDesc("creationtime"))}
 * """
 * }
 * ```
 *
 * @param[attr] The attribute to order by as a [String]
 * @see[selectFrom]
 */
fun byDesc(attr: String): KtFlexSearchOrderBy = by(attr, OrderOp.DESC)

/**
 * Builds an attribute to be used in an `ORDER BY DESC` clause. Must be used along with [order]
 *
 * Example use:
 * ```
 * val p = alias("p")
 * val query = buildQuery {
 * """
 * ${selectFrom("PointOfService" `as` p, leftJoin("Address" `as` a on (p["address"] to a["pk"]), true))}
 * ${where(p["modifiedtime"] gte "2020-10-01")}
 * ${or(a["modifiedtime"] gte "2020-10-01")}
 * ${order(byDesc(p["baseStore"]), byAsc(p["creationtime"]))}
 * """
 * }
 * ```
 *
 * @param[attr] The attribute to order by as a [KtFlexSearchAttr]
 * @see[selectFrom]
 */
fun byDesc(attr: KtFlexSearchAttr): KtFlexSearchOrderBy = by(attr, OrderOp.DESC)

/**
 * Builds an `ORDER BY ASC` clause for a single attribute. Translates to `ORDER BY {attr} ASC` in the Flexible Search
 * Query string.
 *
 * Example use:
 * ```
 * query = buildQuery{
 * """
 * ${selectFrom("Product", distinct = true)}
 * ${where(attr("creationtime") notBetween (Date() to Date()))}
 * ${orderByAsc("pk")}
 * """
 * ```
 *
 * @param[attr] The attribute to order by as a [String]
 * @see[selectFrom]
 */
fun orderByAsc(attr: String): String = orderBy(attr, OrderOp.ASC)

/**
 * Builds an `ORDER BY ASC` clause for a single attribute. Translates to `ORDER BY {attr} ASC` in the Flexible Search
 * Query string.
 *
 * Example use:
 * ```
 * query = buildQuery{
 * """
 * ${selectFrom("Product", distinct = true)}
 * ${where(attr("creationtime") notBetween (Date() to Date()))}
 * ${orderByAsc(attr("pk"))}
 * """
 * ```
 *
 * @param[attr] The attribute to order by as a [KtFlexSearchAttr]
 * @see[selectFrom]
 */
fun orderByAsc(attr: KtFlexSearchAttr): String = orderBy(attr, OrderOp.ASC)

/**
 * Builds an `ORDER BY DESC` clause for a single attribute. Translates to `ORDER BY {attr} DESC` in the Flexible Search
 * Query string.
 *
 * Example use:
 * ```
 * query = buildQuery{
 * """
 * ${selectFrom("Product", distinct = true)}
 * ${where(attr("creationtime") notBetween (Date() to Date()))}
 * ${orderByDesc("pk")}
 * """
 * ```
 *
 * @param[attr] The attribute to order by as a [String]
 * @see[selectFrom]
 */
fun orderByDesc(attr: String): String = orderBy(attr, OrderOp.DESC)

/**
 * Builds an `ORDER BY DESC` clause for a single attribute. Translates to `ORDER BY {attr} DESC` in the Flexible Search
 * Query string.
 *
 * Example use:
 * ```
 * query = buildQuery{
 * """
 * ${selectFrom("Product", distinct = true)}
 * ${where(attr("creationtime") notBetween (Date() to Date()))}
 * ${orderByDesc(attr("pk"))}
 * """
 * ```
 *
 * @param[attr] The attribute to order by as a [String]
 * @see[selectFrom]
 */
fun orderByDesc(attr: KtFlexSearchAttr): String = orderBy(attr, OrderOp.DESC)

/**
 * Builds a `GROUP BY` clause for one or more attributes.
 *
 * Example use:
 * ```
 * val query = buildQuery {
 * """
 * ${selectFieldsFrom("Product", field(count("pk"), Long::class.java), field("name", String::class.java))}
 * ${groupBy("name")}
 * ${having(count("pk") gt 2)}
 * """
 * }
 * ```
 *
 * @param[attrs] The attributes to group by as [String]s
 * @see[selectFieldsFrom]
 * @see[having]
 */
fun groupBy(vararg attrs: String): String {
    val arr = attrs.toList().map { KtFlexSearchAttr(it) }.toTypedArray()
    return groupBy(*arr)
}

/**
 * Builds a `GROUP BY` clause for one or more attributes.
 *
 * Example use:
 * ```
 * val p = alias("p")
 * val query = buildQuery {
 * """
 * ${selectFrom("Product" `as` p)} ${groupBy(p["pk"], p["code"], p["catalogVersion"])} ${orderByDesc(p["code"])}
 * """
 * }.also { it.count = 5 }
 * ```
 *
 * @param[attrs] The attributes to group by as [KtFlexSearchAttr]s
 * @see[selectFrom]
 * @see[order]
 * @see[orderByDesc]
 */
fun groupBy(vararg attrs: KtFlexSearchAttr): String {
    val attrStr = attrs.toList().fold(StringBuilder()) { sb, it -> sb.append("${it}, ") }.dropLast(2).toString()
    return "$GROUP_BY $attrStr"
}


// helper functions
private fun by(attr: String, ascOrDesc: OrderOp): KtFlexSearchOrderBy =
    KtFlexSearchOrderBy(KtFlexSearchAttr(attr), ascOrDesc)

private fun by(attr: KtFlexSearchAttr, ascOrDesc: OrderOp): KtFlexSearchOrderBy =
    KtFlexSearchOrderBy(attr, ascOrDesc)

private fun orderBy(attr: String, ascOrDesc: OrderOp): String = "$ORDER_BY ${by(attr, ascOrDesc)}"
private fun orderBy(attr: KtFlexSearchAttr, ascOrDesc: OrderOp): String = "$ORDER_BY ${by(attr, ascOrDesc)}"
