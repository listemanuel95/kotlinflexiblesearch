package com.github.listemanuel95.flexiblesearch.utils

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery
import com.github.listemanuel95.flexiblesearch.utils.attr as attribute

/**
 * Builds a non-aliased field to select from. The [target] will be added to
 * the [FlexibleSearchQuery]'s `resultClasses`.
 *
 * Example use:
 * ```
 * field(ProductModel.CODE, String::class.java)
 * ```
 *
 * @param[attr] The attribute represented by the field, as a [String]
 * @param[target] The target class. Usually a Java primitive wrapper
 */
fun field(attr: String, target: Class<*>): KtFlexSearchField = KtFlexSearchField(KtFlexSearchAttr(attr), target)

/**
 * Builds a field to select from. The [target] will be added to
 * the [FlexibleSearchQuery]'s `resultClasses`.
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * field(attr(ProductModel.CODE), String::class.java)
 * field(p[ProductModel.CODE], String::class.java)
 * ```
 *
 * @param[attr] The attribute represented by the field, as a [KtFlexSearchAttr]
 * @param[target] The target class. Usually a Java primitive wrapper
 * @see[attribute]
 * @see[alias]
 */
fun field(attr: KtFlexSearchAttr, target: Class<*>): KtFlexSearchField = KtFlexSearchField(attr, target)

/**
 * Builds a field to select from without a `target` class.
 * This is particularly useful for subqueries.
 *
 * Example use:
 * ```
 * field(ProductModel.CODE)
 * ```
 *
 * @param[attr] The attribute represented by the field, as a [String]
 */
fun field(attr: String): KtFlexSearchField = KtFlexSearchField(KtFlexSearchAttr(attr), null)

/**
 * Builds a field to select from without a `target` class.
 * This is particularly useful for subqueries.
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * field(attr(ProductModel.CODE))
 * field(p[ProductModel.CODE])
 * ```
 *
 * @param[attr] The attribute represented by the field, as a [KtFlexSearchAttr]
 * @see[attribute]
 * @see[alias]
 */
fun field(attr: KtFlexSearchAttr): KtFlexSearchField = KtFlexSearchField(attr, null)

/**
 * Builds a non-aliased field to select from and marks it as `DISTINCT` in the query.
 * The [target] will be added to the [FlexibleSearchQuery]'s `resultClasses`.
 *
 * Example use:
 * ```
 * fieldDistinct(ProductModel.CODE, String::class.java)
 * ```
 *
 * @param[attr] The attribute represented by the field, as a [String]
 * @param[target] The target class. Usually a Java primitive wrapper
 */
fun fieldDistinct(attr: String, target: Class<*>): KtFlexSearchField =
    KtFlexSearchField(KtFlexSearchAttr(attr).also { it.distinct = true }, target)

/**
 * Builds a to select from and marks it as `DISTINCT` in the query.
 * The [target] will be added to the [FlexibleSearchQuery]'s `resultClasses`.
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * fieldDistinct(attr(ProductModel.CODE), String::class.java)
 * fieldDistinct(p[ProductModel.CODE], String::class.java)
 * ```
 *
 * @param[attr] The attribute represented by the field, as a [String]
 * @param[target] The target class. Usually a Java primitive wrapper
 * @see[attribute]
 * @see[alias]
 */
fun fieldDistinct(attr: KtFlexSearchAttr, target: Class<*>): KtFlexSearchField =
    KtFlexSearchField(attr.also { it.distinct = true }, target)

/**
 * Builds a field to select from without a `target` class.
 * This is particularly useful for subqueries.
 *
 * Example use:
 * ```
 * fieldDistinct(ProductModel.CODE)
 * ```
 *
 * @param[attr] The attribute represented by the field, as a [String]
 */
fun fieldDistinct(attr: String): KtFlexSearchField =
    KtFlexSearchField(KtFlexSearchAttr(attr).also { it.distinct = true }, null)

/**
 * Builds a field to select from without a `target` class.
 * This is particularly useful for subqueries.
 *
 * Example uses:
 * ```
 * val p = alias("p")
 * fieldDistinct(attr(ProductModel.CODE))
 * fieldDistinct(p[ProductModel.CODE])
 * ```
 *
 * @param[attr] The attribute represented by the field, as a [KtFlexSearchAttr]
 * @see[attribute]
 * @see[alias]
 */
fun fieldDistinct(attr: KtFlexSearchAttr): KtFlexSearchField = KtFlexSearchField(attr.also { it.distinct = true }, null)
