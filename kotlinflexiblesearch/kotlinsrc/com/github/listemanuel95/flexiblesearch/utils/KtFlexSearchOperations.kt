package com.github.listemanuel95.flexiblesearch.utils

/**
 * Builds a `HAVING` clause for the given condition ([KtFlexSearchCondition])
 *
 * Example use (translates to `SELECT COUNT({PK}), {name} FROM {Product} GROUP BY {name} HAVING COUNT({PK}) > 2`):
 * ```
 * val query = buildQuery {
 *   """
 *   ${selectFieldsFrom(ProductModel::class, field(count("pk"), Long::class.java), field("name", String::class.java))}
 *   ${groupBy("name")}
 *   ${having(count("pk") gt 2)}
 *   """
 * }
 * ```
 *
 * @param[condition] The condition to apply the `HAVING` clause to
 * @see[selectFieldsFrom]
 * @see[field]
 * @see[groupBy]
 */
fun having(condition: KtFlexSearchCondition): String {
    KtFlexSearchBuilderState.params.putAll(condition.params)
    return "$HAVING $condition"
}

/**
 * Builds an `AND` operator for the given condition ([KtFlexSearchCondition])
 *
 * Example use (translates to `SELECT {PK} FROM {Product} WHERE {code} IS NOT NULL AND {name} IS NULL`):
 * ```
 * val query = buildQuery {
 *   """
 *   ${selectFrom(ProductModel::class)}
 *   ${where(attr("code").isNotNull())} ${and(attr("name").isNull())}
 *   """
 * }
 * ```
 *
 * @param[condition] The condition to apply the `AND` operator to
 * @param[startBraces] Opens up braces for this condition: `AND (condition`
 * @param[endBraces] Closes up braces for this condition: `AND condition)`
 * @see[selectFrom]
 */
fun and(condition: KtFlexSearchCondition, startBraces: Boolean = false, endBraces: Boolean = false): String {
    KtFlexSearchBuilderState.params.putAll(condition.params)
    val condStr = if (startBraces) {
        "($condition"
    } else if (endBraces) {
        "$condition)"
    } else {
        condition.toString()
    }
    return "$AND $condStr"
}

/**
 * Builds an `AND EXISTS` operator for the given condition ([KtFlexSearchCondition])
 *
 * Example use (translates to `SELECT {PK} FROM {Product} WHERE {name} IS NULL AND EXISTS ({{SELECT UPPER({code}) FROM {Product}}})`):
 * ```
 * val subquery = subquery("${selectFieldsFrom(product::class, field(upper(product.CODE)))}")
 * val query = buildQuery {
 * """
 * ${selectFrom(product::class)} ${where(attr(product.NAME).isNull())}
 * ${andExists(subquery)}
 * """
 * }
 * ```
 *
 * @param[subquery] The subquery to apply the `AND EXISTS` clause to
 * @see[selectFrom]
 */
fun andExists(subquery: KtFlexSearchSubQuery) = "$AND $EXISTS $subquery"

/**
 * Builds an `OR` operator for the given condition ([KtFlexSearchCondition])
 *
 * Example use (translates to `SELECT {PK} FROM {Product} WHERE {code} IS NOT NULL OR {name} IS NULL`):
 * ```
 * val query = buildQuery {
 *   """
 *   ${selectFrom(ProductModel::class)}
 *   ${where(attr("code").isNotNull())} ${or(attr("name").isNull())}
 *   """
 * }
 * ```
 *
 * @param[condition] The condition to apply the `OR` operator to
 * @param[startBraces] Opens up braces for this condition: `OR (condition`
 * @param[endBraces] Closes up braces for this condition: `OR condition)`
 * @see[selectFrom]
 */
fun or(condition: KtFlexSearchCondition, startBraces: Boolean = false, endBraces: Boolean = false): String {
    KtFlexSearchBuilderState.params.putAll(condition.params)
    val condStr = if (startBraces) {
        "($condition"
    } else if (endBraces) {
        "$condition)"
    } else {
        condition.toString()
    }
    return "$OR $condStr"
}

/**
 * Builds an `OR EXISTS` operator for the given condition ([KtFlexSearchCondition])
 *
 * Example use (translates to `SELECT {PK} FROM {Product} WHERE {name} IS NULL OR EXISTS ({{SELECT UPPER({code}) FROM {Product}}})`):
 * ```
 * val subquery = subquery("${selectFieldsFrom(product::class, field(upper(product.CODE)))}")
 * val query = buildQuery {
 * """
 * ${selectFrom(product::class)} ${where(attr(product.NAME).isNull())}
 * ${orExists(subquery)}
 * """
 * }
 * ```
 *
 * @param[subquery] The subquery to apply the `OR EXISTS` clause to
 * @see[selectFrom]
 */
fun orExists(subquery: KtFlexSearchSubQuery) = "$OR $EXISTS $subquery"

/**
 * Builds a `WHERE` clause for the given condition ([KtFlexSearchCondition])
 *
 * Example use (translates to `SELECT {PK} FROM {Product} WHERE {description} IS NOT NULL`):
 * ```
 * val query = buildQuery {
 *   """
 *   ${selectFrom(ProductModel::class)}
 *   ${where(attr(ProductModel.DESCRIPTION).isNotNull())}
 *   """
 * }
 * ```
 *
 * @param[condition] The condition to apply the `HAVING` clause to
 * @see[selectFrom]
 */
fun where(condition: KtFlexSearchCondition): String {
    KtFlexSearchBuilderState.params.putAll(condition.params)
    return "$WHERE $condition"
}

/**
 * Builds a `WHERE EXISTS` clause for the given subquery ([KtFlexSearchSubQuery])
 *
 * Example use (translates to `SELECT {p:pk} FROM {Product AS p}
 * WHERE EXISTS ({{SELECT {m:pk} FROM {Media AS m} WHERE {m:removable}=?removable1 AND {m:modifiedtime}>{p:modifiedtime}}})`
 * ):
 * ```
 * val (p, m) = aliases("p", "m")
 * var subQueryStr = """
 *   ${selectFrom("Media" `as` m)} ${where(m["removable"] eq false)}
 *   ${and(m["modifiedtime"] gt p["modifiedtime"])}
 * """
 * val query = buildQuery { """
 * ${selectFrom("Product" `as` p)}
 * ${whereExists(subquery(subQueryStr))}
 * """
 * }
 * ```
 *
 * @param[subquery] The subquery to apply the `WHERE EXISTS` clause to
 * @see[selectFrom]
 */
fun whereExists(subquery: KtFlexSearchSubQuery): String = "$WHERE $EXISTS $subquery"

/**
 * Builds a `WHERE NOT EXISTS` clause for the given subquery ([KtFlexSearchSubQuery])
 *
 * Example use (translates to `SELECT {p:pk} FROM {Product AS p}
 * WHERE NOT EXISTS ({{SELECT {m:pk} FROM {Media AS m} WHERE {m:removable}=?removable1 AND {m:modifiedtime}>{p:modifiedtime}}})`
 * ):
 * ```
 * val (p, m) = aliases("p", "m")
 * var subQueryStr = """
 *   ${selectFrom("Media" `as` m)} ${where(m["removable"] eq false)}
 *   ${and(m["modifiedtime"] gt p["modifiedtime"])}
 * """
 * val query = buildQuery { """
 * ${selectFrom("Product" `as` p)}
 * ${whereNotExists(subquery(subQueryStr))}
 * """
 * }
 * ```
 *
 * @param[subquery] The subquery to apply the `WHERE NOT EXISTS` clause to
 * @see[selectFrom]
 */
fun whereNotExists(subquery: KtFlexSearchSubQuery): String = "$WHERE NOT $EXISTS $subquery"
