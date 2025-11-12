package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.core.database.entities.DeletedRunSyncEntity
import com.example.core.database.entities.RunPendingSyncEntity

@Dao
interface RunPendingSyncDao {

    //Created Runs
    @Query("SELECT * FROM runpendingsyncentity WHERE userId=:userId")
    suspend fun getAllRunPendingEntities(userId: String): List<RunPendingSyncEntity>

    @Query("SELECT * FROM runpendingsyncentity WHERE runId=:runId")
    suspend fun getRunPendingSyncEntity(runId: String): RunPendingSyncEntity?

    @Upsert
    suspend fun upsertRunPendingSyncEntity(entity: RunPendingSyncEntity)

    @Query("DELETE FROM runpendingsyncentity WHERE runId=:runId")
    suspend fun deleteRunPendingSyncEntity(runId: String)

    //Deleted runs
    @Query("SELECT * FROM deletedrunsyncentity WHERE userId=:userId")
    suspend fun getAllDeletedRunSyncEntities(userId: String): List<DeletedRunSyncEntity>

    @Upsert
    suspend fun upsertDeletedRunSyncEntity(entity: DeletedRunSyncEntity)

    @Query("DELETE FROM deletedrunsyncentity WHERE runId=:runId")
    suspend fun deleteDeletedRunSyncEntity(runId: String)
}