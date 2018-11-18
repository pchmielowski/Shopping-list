package net.chmielowski.shoppinglist.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Singleton
    @JvmStatic
    @Provides
    fun provideDb(context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "room-db"
    )
        .fallbackToDestructiveMigration()
        .build()
}
