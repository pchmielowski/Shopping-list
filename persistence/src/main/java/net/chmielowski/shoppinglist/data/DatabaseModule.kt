package net.chmielowski.shoppinglist.data

import android.content.Context
import android.os.StrictMode
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Singleton
    @JvmStatic
    @Provides
    fun provideDb(context: Context) = slowCall("Creating database") {
        Room.databaseBuilder(context, AppDatabase::class.java, "room-db")
            .fallbackToDestructiveMigration()
            .build()
    }
}

fun <T> slowCall(name: String = "", block: () -> T): T {
    StrictMode.noteSlowCall(name)
    return block()
}
