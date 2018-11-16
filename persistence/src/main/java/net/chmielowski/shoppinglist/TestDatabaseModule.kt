package net.chmielowski.shoppinglist

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides

// TODO: move to test directory
@Module
object TestDatabaseModule {
    @JvmStatic
    @Provides
    fun provideDb(context: Context) = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
}
