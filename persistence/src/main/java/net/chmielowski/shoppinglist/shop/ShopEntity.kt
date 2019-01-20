package net.chmielowski.shoppinglist.shop

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import net.chmielowski.shoppinglist.IconId
import net.chmielowski.shoppinglist.ShopId

@Entity(indices = [Index("name", unique = true)])
data class ShopEntity(
    @PrimaryKey(autoGenerate = true)
    val id: ShopId? = null,
    val name: String,
    @Embedded
    val color: ColorEntity?,
    val icon: IconId
)
