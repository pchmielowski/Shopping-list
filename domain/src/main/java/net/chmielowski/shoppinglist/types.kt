package net.chmielowski.shoppinglist

typealias Id = Long

typealias ObserveShopsType = ObserveData<Unit, List<@JvmSuppressWildcards Shop>>

typealias ObserveItemsType = ObserveData<ReadItemsParams, List<@JvmSuppressWildcards Item>>

typealias ReadItemsType = ActionWithResult<ReadItemsParams, List<@JvmSuppressWildcards Item>>
