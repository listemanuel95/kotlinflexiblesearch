package com.github.listemanuel95.flexiblesearch.utils

/**
 * Wraps an attribute in an SQL `COUNT()` function. Translates to Flexible Search as `COUNT({attr})`.
 *
 * Example use (translates to
 * `SELECT COUNT({PK}), {name} FROM {Product} GROUP BY {name} HAVING COUNT({PK}) > 2`):
 * ```
 * val query = buildQuery {
 * """
 * ${selectFieldsFrom(ProductModel::class, field(count("pk"), Long::class.java), field("name", String::class.java))}
 * ${groupBy("name")}
 * ${having(count("pk") gt 2)}
 * """
 * }
 * ```
 *
 * @param[attr] The attribute to count, as a [String]
 * @see [selectFieldsFrom]
 * @see [groupBy]
 * @see [having]
 */
fun count(attr: String): KtFlexSearchAttr = KtFlexSearchAttr(attr).also { it.count = true }

/**
 * Wraps an attribute in an SQL `COUNT()` function. Translates to Flexible Search as `COUNT({attr})`.
 *
 * Example use (translates to
 * `SELECT COUNT({p:PK}), {p:name} FROM {Product AS p} GROUP BY {p:name} HAVING COUNT({p:PK}) > 2`):
 * ```
 * val p = alias("p")
 * val query = buildQuery {
 * """
 * ${selectFieldsFrom(ProductModel::class `as` p, field(count(p["pk"]), Long::class.java), field(p["name"], String::class.java))}
 * ${groupBy(p["name"])}
 * ${having(count(p["pk"]) gt 2)}
 * """
 * }
 * ```
 *
 * @param[attr] The attribute to count, as a [KtFlexSearchAttr]
 * @see [selectFieldsFrom]
 * @see [groupBy]
 * @see [having]
 */
fun count(attr: KtFlexSearchAttr): KtFlexSearchAttr = attr.also { it.count = true }

/**
 * Wraps an attribute in an SQL `UPPER()` function. Translates to Flexible Search as `UPPER({attr})`.
 *
 * Example use (translates to `SELECT UPPER({code}) FROM {Product}`):
 * ```
 * val query = buildQuery {
 * "${selectFieldsFrom("Product", field(upper("code"), String::class.java))}"
 * }
 * ```
 *
 * @param[attr] The attribute to turn to uppercase, as a [String]
 * @see [selectFieldsFrom]
 */
fun upper(attr: String): KtFlexSearchAttr = KtFlexSearchAttr(attr).also { it.upper = true }

/**
 * Wraps an attribute in an SQL `UPPER()` function. Translates to Flexible Search as `UPPER({attr})`.
 *
 * Example use (translates to `SELECT UPPER({p:code}) FROM {Product AS p}`):
 * ```
 * val p = alias("p")
 * val query = buildQuery {
 * "${selectFieldsFrom("Product" `as` p, field(upper(p["code"]), String::class.java))}"
 * }
 * ```
 *
 * @param[attr] The attribute to turn to uppercase, as a [KtFlexSearchAttr]
 * @see [selectFieldsFrom]
 */
fun upper(attr: KtFlexSearchAttr): KtFlexSearchAttr = attr.also { it.upper = true }

/**
 * Wraps an attribute in an SQL `SUM()` function. Translates to Flexible Search as `SUM({attr})`.
 *
 * Example use (translates to `SELECT SUM({PK}) FROM {Product}`):
 * ```
 * val query = buildQuery("${selectFieldsFrom("Product", field(sum("pk"), Long::class.java))}")
 * ```
 *
 * @param[attr] The attribute to calculate the sum of, as a [String]
 * @see [selectFieldsFrom]
 */
fun sum(attr: String): KtFlexSearchAttr = KtFlexSearchAttr(attr).also { it.sum = true }

/**
 * Wraps an attribute in an SQL `SUM()` function. Translates to Flexible Search as `SUM({attr})`.
 *
 * Example use (translates to `SELECT SUM({p:PK}) FROM {Product AS p}`):
 * ```
 * val p = alias("p")
 * val query = buildQuery("${selectFieldsFrom("Product" `as` p, field(sum(p["pk"]), Long::class.java))}")
 * ```
 *
 * @param[attr] The attribute to calculate the sum of, as a [KtFlexSearchAttr]
 * @see [selectFieldsFrom]
 */
fun sum(attr: KtFlexSearchAttr): KtFlexSearchAttr = attr.also { it.sum = true }

/**
 * Wraps an attribute in an SQL `MIN()` function. Translates to Flexible Search as `MIN({attr})`.
 *
 * Example use (translates to `SELECT MIN({PK}) FROM {Product}`):
 * ```
 * val query = buildQuery("${selectFieldsFrom("Product", field(min("pk"), Long::class.java))}")
 * ```
 *
 * @param[attr] The attribute to find the minimum of, as a [String]
 * @see [selectFieldsFrom]
 */
fun min(attr: String): KtFlexSearchAttr = KtFlexSearchAttr(attr).also { it.min = true }

/**
 * Wraps an attribute in an SQL `MIN()` function. Translates to Flexible Search as `MIN({attr})`.
 *
 * Example use (translates to `SELECT MIN({p:PK}) FROM {Product AS p}`):
 * ```
 * val p = alias("p")
 * val query = buildQuery("${selectFieldsFrom("Product" `as` p, field(min(p["pk"]), Long::class.java))}")
 * ```
 *
 * @param[attr] The attribute to find the minimum of, as a [KtFlexSearchAttr]
 * @see [selectFieldsFrom]
 */
fun min(attr: KtFlexSearchAttr): KtFlexSearchAttr = attr.also { it.min = true }

/**
 * Wraps an attribute in an SQL `MAX()` function. Translates to Flexible Search as `MAX({attr})`.
 *
 * Example use (translates to `SELECT MAX({PK}) FROM {Product}`):
 * ```
 * val query = buildQuery("${selectFieldsFrom("Product", field(max("pk"), Long::class.java))}")
 * ```
 *
 * @param[attr] The attribute to find the maximum of, as a [String]
 * @see [selectFieldsFrom]
 */
fun max(attr: String): KtFlexSearchAttr = KtFlexSearchAttr(attr).also { it.max = true }

/**
 * Wraps an attribute in an SQL `MAX()` function. Translates to Flexible Search as `MAX({attr})`.
 *
 * Example use (translates to `SELECT MAX({p:PK}) FROM {Product AS p}`):
 * ```
 * val p = alias("p")
 * val query = buildQuery("${selectFieldsFrom("Product" `as` p, field(max(p["pk"]), Long::class.java))}")
 * ```
 *
 * @param[attr] The attribute to find the maximum of, as a [KtFlexSearchAttr]
 * @see [selectFieldsFrom]
 */
fun max(attr: KtFlexSearchAttr): KtFlexSearchAttr = attr.also { it.max = true }

/**
 * Wraps an attribute in an SQL `AVG()` function. Translates to Flexible Search as `AVG({attr})`.
 *
 * Example use (translates to `SELECT AVG({PK}) FROM {Product}`):
 * ```
 * val query = buildQuery("${selectFieldsFrom("Product", field(avg("pk"), Long::class.java))}")
 * ```
 *
 * @param[attr] The attribute to find the average of, as a [String]
 * @see [selectFieldsFrom]
 */
fun avg(attr: String): KtFlexSearchAttr = KtFlexSearchAttr(attr).also { it.avg = true }

/**
 * Wraps an attribute in an SQL `AVG()` function. Translates to Flexible Search as `AVG({attr})`.
 *
 * Example use (translates to `SELECT AVG({p:PK}) FROM {Product AS p}`):
 * ```
 * val p = alias("p")
 * val query = buildQuery("${selectFieldsFrom("Product" `as` p, field(avg(p["pk"]), Long::class.java))}")
 * ```
 *
 * @param[attr] The attribute to find the average of, as a [KtFlexSearchAttr]
 * @see [selectFieldsFrom]
 */
fun avg(attr: KtFlexSearchAttr): KtFlexSearchAttr = attr.also { it.avg = true }
