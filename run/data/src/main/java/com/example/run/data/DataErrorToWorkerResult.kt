package com.example.run.data

import androidx.work.ListenableWorker
import com.example.core.domain.util.DataError

fun DataError.toWorkerResult(): ListenableWorker.Result{
    return when(this){
        DataError.Local.DISK_FULL -> ListenableWorker.Result.failure()
        DataError.Network.REQUEST_TIMEOUT -> ListenableWorker.Result.failure()
        DataError.Network.UNAUTHORIZED -> ListenableWorker.Result.failure()
        DataError.Network.CONFLICT -> ListenableWorker.Result.failure()
        DataError.Network.TOO_MANY_REQUESTS -> ListenableWorker.Result.failure()
        DataError.Network.NO_INTERNET -> ListenableWorker.Result.failure()
        DataError.Network.PAYLOAD_TOO_LARGE -> ListenableWorker.Result.failure()
        DataError.Network.SERVER_ERROR -> ListenableWorker.Result.retry()
        DataError.Network.SERIALIZATION -> ListenableWorker.Result.failure()
        DataError.Network.UNKNOWN -> ListenableWorker.Result.failure()
    }
}