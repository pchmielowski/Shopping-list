package net.chmielowski.shoppinglist.data.shop

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.chmielowski.shoppinglist.Id

@Entity
data class ShopEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Id? = null,
    val name: String,
    @Embedded
    val color: ColorEntity?,
    val icon: Id
)
