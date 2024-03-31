package com.android.dev.engineer.kotlin.compose.data.adapter

import com.android.dev.engineer.kotlin.compose.data.use_case.api_error_handling.ApiErrorHandlingUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.lang.reflect.Type

class CallTypeAdapter(
    private val call: Call<Type>,
    private val apiErrorHandlingUseCase: ApiErrorHandlingUseCase
) : Call<Type> by call {
    override fun enqueue(callback: Callback<Type>) {
        call.enqueue(object : Callback<Type> {
            override fun onResponse(call: Call<Type>, response: Response<Type>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    val httpException = HttpException(response)
                    callback.onFailure(call, apiErrorHandlingUseCase.invoke(httpException))
                }
            }

            override fun onFailure(call: Call<Type>, throwable: Throwable) {
                callback.onFailure(call, apiErrorHandlingUseCase.invoke(throwable))
            }
        })
    }
}