package net.chmielowski.shoppinglist.data.shop

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import net.chmielowski.shoppinglist.Id

@Entity(indices = [Index("name", unique = true)])
data class ShopEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Id? = null,
    val name: String,
    @Embedded
    val color: ColorEntity?,
    val icon: Id
)
