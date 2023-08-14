package com.github.listemanuel95.flexiblesearch.utils

import de.hybris.platform.core.model.ItemModel

enum class OrderOp {
    ASC,
    DESC
}

/**
 * Represents a model's attribute inside a Flexible Search Query
 */
open class KtFlexSearchAttr(protected val attr: String) {
    var count = false
    var upper = false
    var distinct = false
    var min = false
    var max = false
    var sum = false
    var avg = false
    var locale = ""

    fun toParam(): String {
        KtFlexSearchBuilderState.paramCounter++
        return "$attr${KtFlexSearchBuilderState.paramCounter}"
    }

    protected open fun attrStr(): String {
        val distinct = if (distinct) "$DISTINCT " else ""
        return if (locale == "") "$distinct{$attr}" else "$distinct{$attr[$locale]}"
    }

    override fun toString(): String {
        return when {
            count -> "$COUNT(${attrStr()})"
            upper -> "$UPPER(${attrStr()})"
            min -> "$MIN(${attrStr()})"
            max -> "$MAX(${attrStr()})"
            sum -> "$SUM(${attrStr()})"
            avg -> "$AVG(${attrStr()})"
            else -> attrStr()
        }
    }
}

/**
 * Represents a model's aliased attribute inside a Flexible Search Query
 */
class KtFlexSearchAliasAttr(private val alias: String, attr: String) : KtFlexSearchAttr(attr) {
    override fun attrStr(): String {
        val distinct = if (distinct) "$DISTINCT " else ""
        val suffix = if (locale == "") attr else "$attr[$locale]"
        return "$distinct{$alias:$suffix}"
    }
}

/**
 * Helper class that builds aliased attributes with the given string
 *
 * @see [KtFlexSearchAliasAttr]
 */
class KtFlexSearchAliasBuilder(val alias: String) {
    /**
     * This is effectively the same as using the overloaded `get` operator.
     * So this two snippets achieve the same result:
     * ```
     * val p = alias("p")
     * val attr1 = p.attr("code")
     * val attr2 = p["code"]
     * ```
     */
    fun attr(attr: String): KtFlexSearchAliasAttr = KtFlexSearchAliasAttr(alias, attr)

    /**
     * This is effectively the same as doing `attr("pk")`
     * So these three snippets achieve the same result:
     * ```
     * val p = alias("p")
     * val pk1 = p.attr("pk")
     * val pk2 = p["pk"]
     * val pk3 = p.pk()
     * ```
     */
    fun pk(): KtFlexSearchAliasAttr = KtFlexSearchAliasAttr(alias, "pk")

    override fun toString(): String = alias
}

/**
 * Represents a field in a `SELECT` clause of a Flexible Search Query.
 *
 * Used for advanced queries where you don't want to obtain an [ItemModel], but rather one or more
 * primitive results (as in: `SELECT {code},{name} FROM {Product}`).
 */
class KtFlexSearchField(private val attr: KtFlexSearchAttr, private val targetClass: Class<*>?) {
    override fun toString(): String = attr.toString()

    init {
        targetClass?.let { KtFlexSearchBuilderState.targetClasses.add(targetClass) }
    }
}

/**
 * Represents a condition within a Flexible Search Query.
 */
class KtFlexSearchCondition(private val cond: String) {
    var params: MutableMap<String, Any> = mutableMapOf()
    override fun toString(): String = cond
}

/**
 * Represents an `ORDER BY` clause within a Flexible Search Query.
 */
class KtFlexSearchOrderBy(private val attr: KtFlexSearchAttr, private val op: OrderOp) {
    override fun toString(): String = "$attr $op"
}

/**
 * Represents an aliased table within a Flexible Search Query.
 * Basically: `FROM {Product AS p}`
 */
class KtFlexSearchAliasedTable(val table: String, val aliasBuilder: KtFlexSearchAliasBuilder?)

/**
 * Represents a `JOIN` clause within a Flexible Search Query.
 */
class KtFlexSearchJoin(
    private val table: KtFlexSearchAliasedTable,
    private val attr1: KtFlexSearchAliasAttr,
    private val attr2: KtFlexSearchAliasAttr
) {
    var outer = false
    var right = false
    var left = false

    override fun toString(): String {
        val (tab, al) = Pair(table.table, table.aliasBuilder)
        return if (left) {
            if (outer) {
                "$LEFT $OUTER $JOIN $tab $AS $al $ON $attr1 = $attr2"
            } else {
                "$LEFT $JOIN $tab $AS $al $ON $attr1 = $attr2"
            }
        } else if (right) {
            if (outer) {
                "$RIGHT $OUTER $JOIN $tab $AS $al $ON $attr1 = $attr2"
            } else {
                "$RIGHT $JOIN $tab $AS $al $ON $attr1 = $attr2"
            }
        } else {
            if (outer) {
                "$OUTER $JOIN $tab $AS $al $ON $attr1 = $attr2"
            } else {
                "$JOIN $tab $AS $al $ON $attr1 = $attr2"
            }
        }
    }
}

/**
 * Represents a `SELECT` clause within a Flexible Search Query.
 */
class KtFlexSearchSelect(
    private val from: KtFlexSearchAliasedTable,
    private val fields: List<KtFlexSearchField> = emptyList(),
    private val joins: List<KtFlexSearchJoin> = emptyList()
) {
    var distinct = false

    override fun toString(): String {
        val (table, alias) = Pair(from.table, from.aliasBuilder)

        return when {
            // simple select clause
            fields.isEmpty() && joins.isEmpty() -> buildSimpleSelect(table, alias)

            // select with fields
            joins.isEmpty() -> {
                if (alias != null) {
                    "$SELECT ${buildFieldsStr()} $FROM {$table $AS $alias}"
                } else {
                    "$SELECT ${buildFieldsStr()} $FROM {$table}"
                }
            }

            // select with joins
            fields.isEmpty() -> {
                if (alias != null) {
                    return if (distinct) {
                        "$SELECT $DISTINCT {$alias:$PK} $FROM {$table $AS $alias ${buildJoinsStr()}}"
                    } else {
                        "$SELECT {$alias:$PK} $FROM {$table $AS $alias ${buildJoinsStr()}}"
                    }
                } else {
                    throw IllegalArgumentException("Error: joins with no alias")
                }
            }

            // select with joins and fields
            else -> buildJoinsAndFieldsStr(table, alias)
        }
    }

    private fun buildSimpleSelect(table: String, alias: KtFlexSearchAliasBuilder?): String {
        return if (alias != null) {
            if (distinct) {
                "$SELECT $DISTINCT {$alias:$PK} $FROM {$table $AS $alias}"
            } else {
                "$SELECT {$alias:$PK} $FROM {$table $AS $alias}"
            }
        } else {
            if (distinct) {
                "$SELECT $DISTINCT {$PK} $FROM {$table}"
            } else {
                "$SELECT {$PK} $FROM {$table}"
            }
        }
    }

    private fun buildJoinsAndFieldsStr(table: String, alias: KtFlexSearchAliasBuilder?): String {
        if (alias != null) {
            return "$SELECT ${buildFieldsStr()} $FROM {$table $AS $alias ${buildJoinsStr()}}"
        } else {
            throw IllegalArgumentException("Error: joins with no alias")
        }
    }

    private fun buildJoinsStr(): String {
        return joins.fold(StringBuilder()) { sb, it -> sb.append("$it ") }.toString()
    }

    private fun buildFieldsStr(): String {
        return fields.fold(StringBuilder()) { sb, it -> sb.append("$it, ") }.dropLast(2).toString()
    }
}

/**
 * Represents a subquery (or inner query) within a Flexible Search Query.
 */
abstract class KtFlexSearchSubQuery {
    abstract var alias: String
}

/**
 * Represents a subquery (or inner query) within a Flexible Search Query.
 */
class KtFlexSearchCommonSubQuery(private val subquery: String) : KtFlexSearchSubQuery() {
    override var alias = ""
    fun raw(): String = subquery.trimIndent().replace("\n", " ")
    override fun toString(): String = "({{${subquery.trimIndent().replace("\n", " ")}}}) $alias"
}

/**
 * Represents a `UNION` subquery (or inner query) within a Flexible Search Query.
 */
class KtFlexSearchUnionSubQuery(
    private val query1: KtFlexSearchCommonSubQuery,
    private val query2: KtFlexSearchCommonSubQuery
) : KtFlexSearchSubQuery() {
    override var alias = ""
    var all = false
    override fun toString(): String {
        return if (all) {
            "({{${query1.raw()}}} $UNION {{${query2.raw()}}}) $alias"
        } else {
            "({{${query1.raw()}}} $UNION_ALL {{${query2.raw()}}}) $alias"
        }
    }
}

/**
 * Represents a `SELECT` clause in a subquery (or inner query) within a Flexible Search Query.
 */
class KtFlexSearchSubQuerySelect(
    private val query: KtFlexSearchSubQuery,
    private val fields: List<KtFlexSearchField> = emptyList()
) {
    var distinct = false
    override fun toString(): String {
        val alias = query.alias
        return if (alias != "") {
            if (fields.isEmpty()) {
                "$SELECT $DISTINCT $alias.PK $FROM $query"
            } else {
                "$SELECT $DISTINCT ${buildFieldsStr()} $FROM $query"
            }
        } else {
            if (fields.isEmpty()) {
                "$SELECT PK $FROM $query"
            } else {
                "$SELECT ${buildFieldsStr()} $FROM $query"
            }
        }
    }

    private fun buildFieldsStr(): String {
        val alias = query.alias
        return if (alias != "") {
            fields.fold(StringBuilder()) { sb, it -> sb.append("$alias.$it, ") }.dropLast(2).toString()
        } else {
            fields.fold(StringBuilder()) { sb, it -> sb.append("$it, ") }.dropLast(2).toString()
        }
    }
}
