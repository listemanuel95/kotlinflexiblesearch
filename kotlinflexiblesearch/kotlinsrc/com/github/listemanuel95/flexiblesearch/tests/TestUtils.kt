package com.github.listemanuel95.flexiblesearch.tests

import de.hybris.platform.core.model.order.OrderEntryModel
import de.hybris.platform.core.model.order.OrderModel
import de.hybris.platform.core.model.product.ProductModel
import de.hybris.platform.core.model.user.AddressModel
import de.hybris.platform.core.model.user.CustomerModel
import de.hybris.platform.deliveryzone.model.ZoneDeliveryModeModel
import de.hybris.platform.deliveryzone.model.ZoneDeliveryModeValueModel
import de.hybris.platform.storelocator.model.PointOfServiceModel

typealias order = OrderModel
typealias orderentry = OrderEntryModel
typealias customer = CustomerModel
typealias product = ProductModel
typealias pos = PointOfServiceModel
typealias address = AddressModel
typealias deliverymode = ZoneDeliveryModeModel
typealias deliveryvalue = ZoneDeliveryModeValueModel

internal object TestUtils {

    object Messages {
        const val QUERY_DOES_NOT_MATCH = "Query doesn't match expected result"
        const val PARAMS_DONT_MATCH = "Wrong amount of query parameters"
        const val PARAM_DOES_NOT_MATCH = "Query param doesn't match"
        const val SEARCH_RESULTS_DONT_MATCH = "Search result classes don't match"
        const val QUERY_COUNT_DOES_NOT_MATCH = "Query count doesn't match"
    }

}
