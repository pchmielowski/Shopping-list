package net.chmielowski.shoppinglist

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {
    @JvmStatic
    @Provides
    fun provideDb(context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "room-db"
    )
        .fallbackToDestructiveMigration()
        .build()
}
