package net.chmielowski.bindinterface

annotation class BindInterface(
    /*
     * List of qualifiers as a full name (e.g. net.chmielowski.shoppinglist.ItemQialifier).
     * TODO: accept classes.
     */
    val qualifiers: Array<String> = []
)
