package net.chmielowski.bindinterface

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class BindInterface(
    /*
     * List of qualifiers as a full name (e.g. net.chmielowski.shoppinglist.ItemQialifier).
     * TODO: accept classes.
     */
    val qualifiers: Array<String> = []
)
