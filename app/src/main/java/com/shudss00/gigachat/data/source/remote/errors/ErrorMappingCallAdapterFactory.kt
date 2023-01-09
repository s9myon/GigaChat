package com.shudss00.gigachat.data.source.remote.errors

import com.shudss00.gigachat.domain.exception.ApiException
import com.shudss00.gigachat.domain.exception.NoInternetException
import com.shudss00.gigachat.domain.exception.UnexpectedException
import retrofit2.*
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

fun mapToDomainException(
    remoteException: Throwable,
    httpExceptionMapper: (HttpException) -> Exception? = { null }
) : Exception {
    return when (remoteException) {
        is IOException -> NoInternetException()
        is HttpException -> httpExceptionMapper(remoteException)
            ?: ApiException(remoteException.code().toString())
        else -> UnexpectedException(remoteException)
    }
}

internal class CallWithErrorHandling(
    private val delegate: Call<Any>
) : Call<Any> by delegate {

    override fun enqueue(callback: Callback<Any>) {
        delegate.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    callback.onFailure(call, mapToDomainException(HttpException(response)))
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                callback.onFailure(call, mapToDomainException(t))
            }
        })
    }
}

internal class ErrorMappingCallAdapter(
    private val delegateAdapter: CallAdapter<Any, Call<*>>
) : CallAdapter<Any, Call<*>> by delegateAdapter {

    override fun adapt(call: Call<Any>): Call<*> {
        return delegateAdapter.adapt(CallWithErrorHandling(call))
    }
}


class ErrorMappingCallAdapterFactory @Inject constructor() : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, Call<*>>? {
        Timber.d("##### ${returnType.toString()}")
        Timber.d("##### ErrorMappingCallAdapterFactory 1")
        if (getRawType(returnType) != Call::class.java
            || returnType !is ParameterizedType
            || returnType.actualTypeArguments.size != 1
        ) {
            return null
        }
        Timber.d("##### ErrorMappingCallAdapterFactory 2")
        val delegate = retrofit.nextCallAdapter(this, returnType, annotations)
        @Suppress("UNCHECKED_CAST")
        return ErrorMappingCallAdapter(
            delegateAdapter = delegate as CallAdapter<Any, Call<*>>
        )
    }
}