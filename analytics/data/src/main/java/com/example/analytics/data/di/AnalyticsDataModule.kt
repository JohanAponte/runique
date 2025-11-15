package com.example.analytics.data.di

import com.example.analytics.data.RoomAnalyticsRepository
import com.example.analytics.domain.AnalyticsRepository
import com.example.core.database.RunDataBase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsModule = module {
    singleOf(::RoomAnalyticsRepository).bind<AnalyticsRepository>()
    single {
        get<RunDataBase>().analyticsDao
    }
}