package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.database.dao.RunDao
import com.example.core.database.entities.RunEntity

@Database(
    entities = [RunEntity::class],
    version = 1
)

abstract class RunDataBase : RoomDatabase() {
    abstract val runDao: RunDao
}