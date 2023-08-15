**kotlinflexiblesearch** is a SAP Commerce extension -written entirely in Kotlin- that provides utility functions to help build **readable**, **reusable** and **concise** flexible search queries without relying on cumbersome constants or magic values.
The extension is heavily inspired by [Flexible search query builder](https://github.com/avrilfanomar/flexiblesearchbuilder)
## Requirements

In order to use the extension, you need your SAP Commerce project to compile and run Kotlin code. You can use [kotlinnature](https://github.com/mlytvyn/kotlinnature/tree/main) for this.
The extension **should** be compatible with any version of SAP Commerce that support Java 8+, but has only been tested in SAP Commerce v2211.
## Installation

Simply add the extension to your `localextensions.xml` file:
``` xml
<!-- Kotlin -->  
<extension name="kotlinnature"/>  
<extension name="kotlinflexiblesearch"/>
```
Then compile the platform with either `ant all` or `ant clean all`.
## Usage
### Simple Example

``` kotlin
import com.github.listemanuel95.flexiblesearch.utils.*
import de.hybris.platform.core.model.product.ProductModel  
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery  
import de.hybris.platform.servicelayer.search.SearchResult

// find products with similar names to the given argument
fun findBySimilarName(name: String): List<ProductModel> {
  val p = alias("p") // optional for this query
  val query: FlexibleSearchQuery = buildQuery {
    """
    ${selectFrom(ProductModel::class `as` p)}
    ${where(p[ProductModel.NAME]) like "%$name%"}
    """
  }
  // assumes you've injected a FlexibleSearchService bean into the `flexibleSearchService` variable
  val result: SearchResult<ProductModel> = flexibleSearchService.search(query)
  return result.result // get the results as a List and return them
}
```

### Advanced Examples

#### Multiple Joins Query

```kotlin

import com.github.listemanuel95.flexiblesearch.utils.*
import de.hybris.platform.core.model.order.OrderEntryModel
import de.hybris.platform.core.model.order.OrderModel
import de.hybris.platform.core.model.product.ProductModel

typealias Product = ProductModel
typealias Entry = OrderEntryModel
typealias Order = OrderModel

fun multipleJoins(): List<ProductModel> {
  val (p, oe, o) = aliases("p", "oe", "o")
  val query = buildQuery {
    """
    ${selectFieldsFrom(Product::class `as` p, 
        listOf(field(count(o[Order.CODE])), field(p[Product.CODE])),
        join(Entry::class `as` oe on (p.pk() to oe[Entry.PRODUCT])),
        join(Order::class `as` o on (o.pk() to oe[Entry.ORDER]))
    )} ${groupBy(p[Product.CODE])} ${orderByDesc(count(o[Order.CODE]))}
    """
  }

  // assumes you've injected a FlexibleSearchService bean into the `flexibleSearchService` variable
  val result: SearchResult<ProductModel> = flexibleSearchService.search(query)
  return result.result // get the results as a List and return them
}
```
Translates to:
```sql
SELECT COUNT({o:code}), {p:code} FROM 
  {Product AS p 
      JOIN OrderEntry AS oe ON {p:pk} = {oe.product} 
      JOIN Order AS o ON {o:pk} = {oe:order}}
  GROUP BY {p:code} ORDER BY COUNT({o:code}) DESC
```

#### Using Subqueries

```kotlin
import de.hybris.platform.core.model.product.ProductModel

typealias Product = ProductModel

fun notInSubquery() {
  val query = buildQuery {
    """
    ${selectFrom(Product::class)} 
    ${where(attr(Product.CODE) notInSubquery commonSubquery)}
    """
  }
}

fun existsSubquery() {
  val p = alias("p")
  val query = buildQuery{ 
    """
    ${selectFrom(Product::class `as` p)} ${whereExists(commonSubquery)}
    """
  }
}

companion object {
  private val commonSubquery = 
      subquery("${selectFieldsFrom(Product::class, field(upper(Product.CODE)))}")
}
```
Respectively translate to:
```sql
SELECT {PK} FROM {Product} 
WHERE {code} NOT IN ({{SELECT UPPER({code}) FROM {Product}}})

SELECT {p:PK} FROM {Product AS p} 
WHERE EXISTS ({{SELECT UPPER({code}) FROM {Product}}})
```

### More Examples
You can check the [Unit Tests](https://github.com/listemanuel95/kotlinflexiblesearch/tree/main/kotlinflexiblesearch/kotlinsrc/com/github/listemanuel95/flexiblesearch/tests) for exhaustive examples.

## Tips & Tricks

### String Templates
Since we're using String Templates. Everything evaluates to a String in the end. That means doing this:
``` kotlin
val subquery = subquery("${selectFieldsFrom(product::class, field(upper(product.CODE)))}")  
val query = buildQuery {  
  """  
   ${selectFrom(product::class)} ${where(attr(product.NAME).isNull())}
   ${and(attr(product.CODE) insub subquery, startBraces = true)}   
   ${or(attr(product.NAME).isNotNull(), endBraces = true)}  
  """  
}
```

Is effectively the same as doing this (which greatly improves readability, though adding some pesky constants)
``` kotlin
const val AND = "AND" // in companion object

val subquery = subquery("${selectFieldsFrom(product::class, field(upper(product.CODE)))}")  
val query = buildQuery {  
  """  
   ${selectFrom(product::class)} ${where(attr(product.NAME).isNull())}
   $AND ( ${(attr(product.CODE) insub subquery)}   
   ${or(attr(product.NAME).isNotNull())} )  
  """  
}
```

### Readability
I recommend using `typealias`, **named parameters** and **variable extraction** everywhere possible to enhance the readability of the queries. Compare these two snippets as an example:

Without:
``` kotlin
// Obtains recently modified Points of Service ordered by BaseStore and creationtime

val (p, a) = aliases("p", "a")  
val query = buildQuery {  
  """  
  ${selectFrom(  
    PointOfServiceModel::class `as` p,  
    leftJoin(AddressModel::class `as` a on (p[PointOfServiceModel.ADDRESS] to a.pk()), true)  
  )}
  ${where(p[PointOfServiceModel.MODIFIEDTIME] gte "2020-10-01")}  
  ${or(a[AddressModel.MODIFIEDTIME] gte "2020-10-01")}  
  ${order(byDesc(p[PointOfServiceModel.BASESTORE]), byAsc(p[PointOfServiceModel.CREATIONTIME]))}  
  """  
}
```

With:
``` kotlin
// Obtains recently modified Points of Service ordered by BaseStore and creationtime

// top level
typealias PoS = PointOfServiceModel
typealias Address = AddressModel

val (p, a) = aliases("p", "a")  
val selectClause = selectFrom(  
  PoS::class `as` p,  
  leftJoin(Address::class `as` a on (p[PoS.ADDRESS] to a.pk()), outer = true)  
)  
val query = buildQuery {  
  """  
  $selectClause  
  ${where(p[PoS.MODIFIEDTIME] gte "2020-10-01")}  
  ${or(a[Address.MODIFIEDTIME] gte "2020-10-01")}  
  ${order(byDesc(p[PoS.BASESTORE]), byAsc(p[PoS.CREATIONTIME]))}  
  """  
}
```
