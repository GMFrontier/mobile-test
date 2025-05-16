package com.frommetoyou.core.util

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response

fun <T> Response<T>.parseResponse(): Result<T> {
    val responseBody = body()
    return if(responseBody != null)
        try {
            Result.success(responseBody)
        } catch (e: Exception) {
            Result.failure(e)
        }
   // else getErrorResponse(errorBody(), code())
    else Result.failure(Throwable("Generic error"))
}
/*

fun getErrorResponse(errorBody: ResponseBody?, respinseCode: Int) : Result {
    try {
        val errorMessage = if(errorBody != null)
            Gson().fromJson(errorBody.charStream(), ErrorResponse::class)
    }
}*/
