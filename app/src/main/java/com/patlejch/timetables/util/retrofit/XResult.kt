package com.patlejch.timetables.util.retrofit

import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.io.IOException

fun <T : Any> Response<T>.toResult(): Result<T?> {
    if (isSuccessful) {
        val body = body()
        return if (body != null) {
            Result.success(body)
        } else {
            Result.success(null)
        }
    }

    return Result.failure(IOException("Api error ${code()} ${message()}"))
}

suspend fun <T : Any> Deferred<Response<T>>.awaitResult(): Result<T?> {
    return try {
        await().toResult()
    } catch (e: Exception) {
        Result.failure(e)
    }
}

fun <T : Any> Result<T?>.requireNotNull() = runCatching { getOrThrow()!! }
    .map { Result.success(it) }
    .getOrElse { Result.failure(exceptionOrNull() ?: UnknownError()) }
