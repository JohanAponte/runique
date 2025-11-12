package com.example.core.database.di

import androidx.room.Room
import com.example.core.database.RoomLocalRunDataSource
import com.example.core.database.RunDataBase
import com.example.core.domain.run.LocalRunDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            RunDataBase::class.java,
            "run.db"
        ).build()
    }

    single { get<RunDataBase>().runDao }
    single { get<RunDataBase>().runPendingSyncDao }

    singleOf(::RoomLocalRunDataSource).bind<LocalRunDataSource>()
}