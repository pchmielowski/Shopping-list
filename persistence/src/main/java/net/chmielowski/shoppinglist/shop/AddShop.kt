package net.chmielowski.shoppinglist.shop

import net.chmielowski.shoppinglist.ActionWithResult

class AddShop(private val repo: ShopRepository) : ActionWithResult<AddShopParams, AddShopResult> {
    override fun invoke(params: AddShopParams) = repo.add(params.toEntity())
        .map<AddShopResult> { id -> AddShopResult.Success(id) }
        .onErrorReturn { AddShopResult.ShopAlreadyPresent }!!

    private fun AddShopParams.toEntity() =
        ShopEntity(name, color, icon)
}
