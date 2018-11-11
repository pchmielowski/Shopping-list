package net.chmielowski.shoppinglist

data class Item(
    override val id: Id,
    val name: String,
    val completed: Boolean,
    val quantity: Quantity?
) : HasId {
    sealed class Quantity {
        data class NoUnit(val value: Int) : Quantity()
        data class Weight(val value: Float) : Quantity()
    }
}
