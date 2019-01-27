package net.chmielowski.shoppinglist

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val persistenceModule
    get() = module {
        factory {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, "room-db")
                .build()
        }


        factory { get<AppDatabase>().itemDao }
        factory { get<AppDatabase>().shopDao }
    }
