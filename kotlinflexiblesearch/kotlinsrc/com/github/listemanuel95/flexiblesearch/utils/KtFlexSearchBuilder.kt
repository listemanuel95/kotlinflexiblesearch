package com.github.listemanuel95.flexiblesearch.utils

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery
import java.util.*
import kotlin.reflect.KClass


internal object KtFlexSearchBuilderState {
    var paramCounter: Int = 0
    val params: MutableMap<String, Any?> = mutableMapOf()
    val targetClasses: MutableList<Class<*>> = mutableListOf()

    fun reset() {
        params.clear()
        targetClasses.clear()
        paramCounter = 0
    }
}

/**
 * Builds a subquery from a [String]
 *
 * Example use:
 * ```
 * subquery("${selectFrom(ProductModel::class)} ${where(ProductModel.CODE isNotNull)}")`
 * ```
 *
 * @param[subquery] A properly built subquery [String]
 * @see[selectFrom]
 */
fun subquery(subquery: String): KtFlexSearchCommonSubQuery = KtFlexSearchCommonSubQuery(subquery)

/**
 * Builds a subquery from a *Select Clause*: [KtFlexSearchSelect]
 *
 * Example use:
 * ```
 * subquery(selectFieldsFrom("Product", field(upper("code"))))
 * ```
 *
 * @param[subquery] A select clause [KtFlexSearchSelect]
 * @see[selectFieldsFrom]
 */
fun subquery(subquery: KtFlexSearchSelect): KtFlexSearchCommonSubQuery = KtFlexSearchCommonSubQuery(subquery.toString())

/**
 * Builds a [FlexibleSearchQuery] with the given [String]
 *
 * Example use:
 * ```
 * buildQuery("${selectFieldsFrom("Product", field(upper("code")))}")
 * ```
 *
 * @param[query] The query [String]
 * @see[selectFieldsFrom]
 */
fun buildQuery(query: String): FlexibleSearchQuery {
    val trimmed: String = query.trimIndent().replace("\n", " ")
    val fQuery = FlexibleSearchQuery(trimmed).also {
        it.addQueryParameters(KtFlexSearchBuilderState.params.toMap())
        if (KtFlexSearchBuilderState.targetClasses.isNotEmpty()) {
            it.setResultClassList(KtFlexSearchBuilderState.targetClasses.toList())
        }
        KtFlexSearchBuilderState.reset()
    }
    return fQuery
}

/**
 * Builds a [FlexibleSearchQuery] with the [String] return of the given lambda
 *
 * Example use:
 * ```
 * buildQuery { """
 * "${selectFieldsFrom("Product", field(upper("code")))}"
 * """
 * }
 * ```
 *
 * @param[strLambda] The lambda that returns the query
 * @see[selectFieldsFrom]
 */
fun buildQuery(strLambda: () -> String): FlexibleSearchQuery = buildQuery(strLambda())

// helper functions
internal fun getTypeCode(clazz: KClass<*>): String {
    val typeCode: String = clazz.java.getField(TYPECODE_FIELD)[Optional.empty<Any>()].toString()
    return if (typeCode != "") typeCode else throw IllegalArgumentException("Invalid class: $clazz")
}
