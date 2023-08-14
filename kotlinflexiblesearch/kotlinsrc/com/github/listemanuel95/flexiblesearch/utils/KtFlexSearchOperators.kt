package com.github.listemanuel95.flexiblesearch.utils

/**
 * Builds an "equals" condition for the given attribute.
 * Translates to `{attr} = `[to] in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("code") eq "1234"}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["code"] eq "1234"}")
 * ```
 *
 * @param[to] The target for the comparison. Can be [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.eq(to: Any): KtFlexSearchCondition = this.buildCondition("$this = ", to)

/**
 * Builds a "not equals" condition for the given attribute.
 * Translates to `{attr} <> `[to] in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("code") notEq "1234"}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["code"] notEq "1234"}")
 * ```
 *
 * @param[to] The target for the comparison. Can be [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.notEq(to: Any): KtFlexSearchCondition = this.buildCondition("$this <> ", to)

/**
 * Builds a "not equals" condition for the given attribute.
 * Translates to `{attr} <> `[to] in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("code") neq "1234"}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["code"] neq "1234"}")
 * ```
 *
 * @param[to] The target for the comparison. Can be [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.neq(to: Any): KtFlexSearchCondition = this.buildCondition("$this <> ", to)

/**
 * Builds a "greater than" condition for the given attribute.
 * Translates to `{attr} > `[to] in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val someDate = Date()
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("modifiedtime") gt someDate}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["modifiedtime"] gt someDate}")
 * ```
 *
 * @param[to] The target for the comparison. Can be [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.gt(to: Any): KtFlexSearchCondition = this.buildCondition("$this > ", to)

/**
 * Builds a "greater than" condition for the given attribute.
 * Translates to `{attr} > `[to] in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val someDate = Date()
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("modifiedtime") greater someDate}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["modifiedtime"] greater someDate}")
 * ```
 *
 * @param[to] The target for the comparison. Can be [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.greater(to: Any): KtFlexSearchCondition = this.buildCondition("$this > ", to)

/**
 * Builds a "greater than or equals" condition for the given attribute.
 * Translates to `{attr} >= `[to] in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val someDate = Date()
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("modifiedtime") gte someDate}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["modifiedtime"] gte someDate}")
 * ```
 *
 * @param[to] The target for the comparison. Can be [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.gte(to: Any): KtFlexSearchCondition = this.buildCondition("$this >= ", to)

/**
 * Builds a "greater than or equals" condition for the given attribute.
 * Translates to `{attr} >= `[to] in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val someDate = Date()
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("modifiedtime") greaterEq someDate}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["modifiedtime"] greaterEq someDate}")
 * ```
 *
 * @param[to] The target for the comparison. Can be [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.greaterEq(to: Any): KtFlexSearchCondition =
    this.buildCondition("$this >= ", to)

/**
 * Builds a "lower than" condition for the given attribute.
 * Translates to `{attr} < `[to] in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val someDate = Date()
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("modifiedtime") lt someDate}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["modifiedtime"] lt someDate}")
 * ```
 *
 * @param[to] The target for the comparison. Can be [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.lt(to: Any): KtFlexSearchCondition = this.buildCondition("$this < ", to)

/**
 * Builds a "lower than" condition for the given attribute.
 * Translates to `{attr} < `[to] in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val someDate = Date()
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("modifiedtime") lower someDate}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["modifiedtime"] lower someDate}")
 * ```
 *
 * @param[to] The target for the comparison. Can be [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.lower(to: Any): KtFlexSearchCondition = this.buildCondition("$this < ", to)

/**
 * Builds a "lower than or equals" condition for the given attribute.
 * Translates to `{attr} <= `[to] in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val someDate = Date()
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("modifiedtime") lte someDate}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["modifiedtime"] lte someDate}")
 * ```
 *
 * @param[to] The target for the comparison. Can be [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.lte(to: Any): KtFlexSearchCondition = this.buildCondition("$this <= ", to)

/**
 * Builds a "lower than or equals" condition for the given attribute.
 * Translates to `{attr} <= `[to] in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val someDate = Date()
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("modifiedtime") lowerEq someDate}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["modifiedtime"] lowerEq someDate}")
 * ```
 *
 * @param[to] The target for the comparison. Can be [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.lowerEq(to: Any): KtFlexSearchCondition =
    this.buildCondition("$this <= ", to)

/**
 * Builds a "null" condition for the given attribute.
 * Translates to `{attr} IS NULL` in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("description").isNull()}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["description"].isNull()}")
 * ```
 *
 * @see[selectFrom]
 */
fun KtFlexSearchAttr.isNull(): KtFlexSearchCondition = KtFlexSearchCondition("$this IS NULL")

/**
 * Builds a "non null" condition for the given attribute.
 * Translates to `{attr} IS NOT NULL` in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("description").isNotNull()}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["description"].isNotNull()}")
 * ```
 *
 * @see[selectFrom]
 */
fun KtFlexSearchAttr.isNotNull(): KtFlexSearchCondition = KtFlexSearchCondition("$this IS NOT NULL")

/**
 * Builds an "IN" condition for the given attribute.
 * Translates to `{attr} IN (list1, list2, ...)` in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val codes = listOf("1234", "5678")
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("code") `in` codes}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["code"] `in` codes}")
 * ```
 *
 * @param[list] The target for the comparison. Must be a [List] of [Any] objects
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.`in`(list: List<Any>): KtFlexSearchCondition =
    this.buildCondition("$this IN ", list)

/**
 * Builds an "IN" condition for the given attribute.
 * Translates to `{attr} IN (list1, list2, ...)` in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val codes = listOf("1234", "5678")
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("code") inList codes}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["code"] inList codes}")
 * ```
 *
 * @param[list] The target for the comparison. Must be a [List] of [Any] objects
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.inList(list: List<Any>): KtFlexSearchCondition =
    this.buildCondition("$this IN ", list)

/**
 * Builds a "NOT IN" condition for the given attribute.
 * Translates to `{attr} NOT IN (list1, list2, ...)` in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val codes = listOf("1234", "5678")
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("code") notIn codes}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["code"] notIn codes}")
 * ```
 *
 * @param[list] The target for the comparison. Must be a [List] of [Any] objects
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.notIn(list: List<Any>): KtFlexSearchCondition =
    this.buildCondition("$this NOT IN ", list)

/**
 * Builds a "NOT IN" condition for the given attribute.
 * Translates to `{attr} NOT IN (list1, list2, ...)` in the Flexible Search Query
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * val codes = listOf("1234", "5678")
 * val query1 = buildQuery("${selectFrom(ProductModel::class)} ${where(attr("code") notInList codes}")
 * val query2 = buildQuery("${selectFrom(ProductModel::class) `as` p} ${where(p["code"] notInList codes}")
 * ```
 *
 * @param[list] The target for the comparison. Must be a [List] of [Any] objects
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.notInList(list: List<Any>): KtFlexSearchCondition =
    this.buildCondition("$this NOT IN ", list)

/**
 * Builds an "IN" condition for the given attribute.
 * Translates to `{attr} IN ({{subquery}})` in the Flexible Search Query
 *
 * Example use:
 * ```
 * val subquery = subquery("${selectFieldsFrom(ProductModel::class, field(upper("code"), Long::class.java))}")
 * var query = buildQuery {
 * """
 * ${selectFrom(ProductModel::class)}
 * ${where(attr("code") insub subquery)}
 * """
 * }
 * ```
 *
 * @param[subquery] The target subquery for the comparison
 * @see[selectFieldsFrom]
 */
infix fun KtFlexSearchAttr.insub(subquery: KtFlexSearchSubQuery): KtFlexSearchCondition =
    KtFlexSearchCondition("$this IN $subquery")

/**
 * Builds an "IN" condition for the given attribute.
 * Translates to `{attr} IN ({{subquery}})` in the Flexible Search Query
 *
 * Example use:
 * ```
 * val subquery = subquery("${selectFieldsFrom(ProductModel::class, field(upper("code"), Long::class.java))}")
 * var query = buildQuery {
 * """
 * ${selectFrom(ProductModel::class)}
 * ${where(attr("code") inSub subquery)}
 * """
 * }
 * ```
 *
 * @param[subquery] The target subquery for the comparison
 * @see[selectFieldsFrom]
 */
infix fun KtFlexSearchAttr.inSubquery(subquery: KtFlexSearchSubQuery): KtFlexSearchCondition =
    KtFlexSearchCondition("$this IN $subquery")


/**
 * Builds a "NOT IN" condition for the given attribute.
 * Translates to `{attr} NOT IN ({{subquery}})` in the Flexible Search Query
 *
 * Example use:
 * ```
 * val subquery = subquery("${selectFieldsFrom(ProductModel::class, field(upper("code"), Long::class.java))}")
 * var query = buildQuery {
 * """
 * ${selectFrom(ProductModel::class)}
 * ${where(attr("code") notInSub subquery)}
 * """
 * }
 * ```
 *
 * @param[subquery] The target subquery for the comparison
 * @see[selectFieldsFrom]
 */
infix fun KtFlexSearchAttr.notInsub(subquery: KtFlexSearchSubQuery): KtFlexSearchCondition =
    KtFlexSearchCondition("$this NOT IN $subquery")


/**
 * Builds a "NOT IN" condition for the given attribute.
 * Translates to `{attr} NOT IN ({{subquery}})` in the Flexible Search Query
 *
 * Example use:
 * ```
 * val subquery = subquery("${selectFieldsFrom(ProductModel::class, field(upper("code"), Long::class.java))}")
 * var query = buildQuery {
 * """
 * ${selectFrom(ProductModel::class)}
 * ${where(attr("code") notInSubquery subquery)}
 * """
 * }
 * ```
 *
 * @param[subquery] The target subquery for the comparison
 * @see[selectFieldsFrom]
 */
infix fun KtFlexSearchAttr.notInSubquery(subquery: KtFlexSearchSubQuery): KtFlexSearchCondition =
    KtFlexSearchCondition("$this NOT IN $subquery")

/**
 * Builds a "LIKE" condition for the given attribute.
 * Translates to `{attr} LIKE `[to] in the Flexible Search Query
 *
 * Example use:
 * ```
 * val query = buildQuery {
 * """
 * ${selectFrom("Product")} ${where(attr("code") like "%23%")}
 * """
 * }
 * ```
 *
 * @param[to] The target for the comparison. Can be [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.like(to: Any): KtFlexSearchCondition =
    buildCondition("$this LIKE ", to)

/**
 * Builds a "NOT LIKE" condition for the given attribute.
 * Translates to `{attr} NOT LIKE `[to] in the Flexible Search Query
 *
 * Example use:
 * ```
 * val query = buildQuery {
 * """
 * ${selectFrom("Product")} ${where(attr("code") notLike "%23%")}
 * """
 * }
 * ```
 *
 * @param[to] The target for the comparison. Can be [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.notLike(to: Any): KtFlexSearchCondition =
    this.buildCondition("$this NOT LIKE ", to)

/**
 * Builds a "BETWEEN" condition for the given attribute.
 * Translates to `{attr} BETWEEN to.first AND to.second` in the Flexible Search Query
 *
 * Example use:
 * ```
 * val date1 = Date()
 * val date2 = Date()
 * val query = buildQuery { """
 * ${selectFrom(ProductModel::class)}
 * ${where(attr("creationtime") between (date1 to date2))}
 * """
 * }
 * ```
 *
 * @param[attrs] The targets for the comparison. Must be a [Pair] of [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.between(attrs: Pair<Any, Any>): KtFlexSearchCondition =
    buildCondition("$this BETWEEN ", attrs.first, attrs.second)


/**
 * Builds a "NOT BETWEEN" condition for the given attribute.
 * Translates to `{attr} NOT BETWEEN to.first AND to.second` in the Flexible Search Query
 *
 * Example use:
 * ```
 * val date1 = Date()
 * val date2 = Date()
 * val query = buildQuery { """
 * ${selectFrom(ProductModel::class)}
 * ${where(attr("creationtime") notBetween (date1 to date2))}
 * """
 * }
 * ```
 *
 * @param[attrs] The targets for the comparison. Must be a [Pair] of [Any] object
 * @see[selectFrom]
 */
infix fun KtFlexSearchAttr.notBetween(attrs: Pair<Any, Any>): KtFlexSearchCondition =
    this.buildCondition("$this NOT BETWEEN ", attrs.first, attrs.second)

/**
 * Builds a "UNION" clause between two queries.
 * Translates to `({{query1}} UNION {{query2}})` in the Flexible Search Query
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
 * @param[query] The query to apply the `UNION` clause with
 * @see[selectFrom]
 * @see[join]
 */
infix fun KtFlexSearchCommonSubQuery.union(query: KtFlexSearchCommonSubQuery): KtFlexSearchUnionSubQuery =
    KtFlexSearchUnionSubQuery(this, query)

/**
 * Builds a "UNION ALL" clause between two queries.
 * Translates to `({{query1}} UNION ALL {{query2}})` in the Flexible Search Query
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
 * val query = buildQuery("${selectFromSubquery(subquery1 unionAll subquery2 `as` "query", distinct = true)}")
 * ```
 *
 * @param[query] The query to apply the `UNION` clause with
 * @see[selectFrom]
 * @see[join]
 */
infix fun KtFlexSearchCommonSubQuery.unionAll(query: KtFlexSearchCommonSubQuery): KtFlexSearchUnionSubQuery =
    KtFlexSearchUnionSubQuery(this, query).also { it.all = true }


// helper functions
private fun KtFlexSearchAttr.buildCondition(str: String, attr: Any): KtFlexSearchCondition {
    val toParam: String = this.toParam()
    return when (attr) {
        is KtFlexSearchAttr -> KtFlexSearchCondition("$str $attr")
        else -> KtFlexSearchCondition("$str ?$toParam").also { it.params[toParam] = attr }
    }
}

private fun KtFlexSearchAttr.buildCondition(str: String, attr1: Any, attr2: Any): KtFlexSearchCondition {
    var condStr = str
    val params = mutableMapOf<String, Any>()
    condStr += when (attr1) {
        is KtFlexSearchAttr -> " $attr1"
        else -> {
            val toParam = this.toParam()
            params[toParam] = attr1
            " ?$toParam"
        }
    }
    condStr += " $AND "
    condStr += when (attr2) {
        is KtFlexSearchAttr -> " $attr2"
        else -> {
            val toParam = this.toParam()
            params[toParam] = attr2
            " ?$toParam"
        }
    }
    return KtFlexSearchCondition(condStr).also { it.params.putAll(params) }
}

private fun KtFlexSearchAttr.buildCondition(str: String, attrs: List<Any>): KtFlexSearchCondition {
    var condStr = "$str ("
    val params = mutableMapOf<String, Any>()
    for (attr in attrs) {
        condStr += when (attr) {
            is KtFlexSearchAttr -> "$attr, "
            else -> {
                val toParam: String = this.toParam()
                params[toParam] = attr
                "?$toParam, "
            }
        }
    }
    return KtFlexSearchCondition("${condStr.dropLast(2)})").also { it.params.putAll(params) }
}
