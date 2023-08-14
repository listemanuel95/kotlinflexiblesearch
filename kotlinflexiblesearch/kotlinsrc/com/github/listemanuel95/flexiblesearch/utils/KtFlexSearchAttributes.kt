package com.github.listemanuel95.flexiblesearch.utils

/**
 * Builds an attribute referencing the `ItemModel`'s PK
 *
 * It's the same as calling `attr("PK")`
 *
 * @see[attr]
 */
fun pk(): KtFlexSearchAttr = KtFlexSearchAttr("pk")

/**
 * Builds a non-aliased attribute
 *
 * Example use:
 * ```
 * attr(ProductModel.CODE)
 * ```
 *
 * For aliased attributes, use [KtFlexSearchAliasBuilder] by calling [alias] or [aliases]
 *
 * @param[attr] The name of the attribute in the *ItemModel*
 * @see[alias]
 * @see[aliases]
 */
fun attr(attr: String): KtFlexSearchAttr = KtFlexSearchAttr(attr)

/**
 * Builds a non-aliased localized attribute
 *
 * Example use:
 * ```
 * loc(ProductModel.CODE, "en")
 * ```
 *
 * For localized aliased attributes, use [loc] with an already built aliased attribute
 *
 * @param[attr] The name of the attribute in the *ItemModel*
 * @param[locale] The ISO of the locale expected by the *FlexibleQuery*
 */
fun loc(attr: String, locale: String): KtFlexSearchAttr = KtFlexSearchAttr(attr).also { it.locale = locale }

/**
 * Builds a localized attribute
 *
 * Example for a non-aliased attribute:
 * ```
 * loc(attr(ProductModel.CODE), "en")
 * ```
 *
 * Example for an aliased attribute:
 * ```
 * loc(alias("p")[ProductModel.CODE], "en")
 * ```
 *
 * @param[attr] The name of the attribute in the *ItemModel*
 * @param[locale] The ISO of the locale expected by the *FlexibleQuery*
 * @see[alias]
 */
fun loc(attr: KtFlexSearchAttr, locale: String): KtFlexSearchAttr = attr.also { it.locale = locale }
