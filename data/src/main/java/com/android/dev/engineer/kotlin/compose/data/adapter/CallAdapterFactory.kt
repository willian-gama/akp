package com.android.dev.engineer.kotlin.compose.data.adapter

import com.android.dev.engineer.kotlin.compose.data.use_case.api_error_handling.ApiErrorHandlingUseCase
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Inject

class CallAdapterFactory @Inject constructor(
    private val apiErrorHandlingUseCase: ApiErrorHandlingUseCase
) : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<Type, Call<Type>> {
        val defaultCallAdapter = retrofit.nextCallAdapter(this, returnType, annotations)
        return object : CallAdapter<Type, Call<Type>> {
            override fun responseType(): Type {
                return defaultCallAdapter.responseType()
            }

            override fun adapt(call: Call<Type>): Call<Type> {
                return CallTypeAdapter(
                    call = call,
                    apiErrorHandlingUseCase = apiErrorHandlingUseCase
                )
            }
        }
    }
}