package com.example.run.network

import com.example.core.data.networking.constructRout
import com.example.core.data.networking.delete
import com.example.core.data.networking.get
import com.example.core.data.networking.safeCall
import com.example.core.domain.run.RemoteRunDataSource
import com.example.core.domain.run.Run
import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyDataResult
import com.example.core.domain.util.Result
import com.example.core.domain.util.map
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement


class KtorRemoteRunDataSource(
    private val httpClient: HttpClient
) : RemoteRunDataSource {
    override suspend fun getRuns(): Result<List<Run>, DataError.Network> {
        return httpClient.get<List<RunDto>>(
            route = "/runs"
        ).map { runDtos ->
            runDtos.map { it.toRun() }
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun postRun(
        run: Run,
        mapPicture: ByteArray
    ): Result<Run, DataError.Network> {
        val createRunRequestJson = Json.encodeToJsonElement(run.toCreateRunRequest())
        val result = safeCall<RunDto> {
            httpClient.submitFormWithBinaryData(
                url = constructRout(route = "/run"),
                formData = formData {
                    append("MAP_PICTURE", mapPicture, Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(HttpHeaders.ContentDisposition, "filename=mappicture.jpg")
                    })
                    append("RUN_DATA", createRunRequestJson, Headers.build {
                        append(HttpHeaders.ContentType, "text/plain")
                        append(HttpHeaders.ContentDisposition, "form-data; name=\"RUN_DATA\"")
                    })
                }
            ) {
                method = HttpMethod.Post
            }
        }
        return result.map { runDto ->
            runDto.toRun()
        }
    }

    override suspend fun deleteRun(id: String): EmptyDataResult<DataError.Network> {
        return httpClient.delete(
            route = "/run",
            queryParameters = mapOf(
                "id" to id
            )
        )
    }
}