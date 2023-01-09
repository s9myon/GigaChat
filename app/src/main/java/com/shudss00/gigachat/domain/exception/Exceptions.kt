package com.shudss00.gigachat.domain.exception

data class ApiException(override val message: String) : Exception()

class NoInternetException: Exception()

class UnexpectedException(cause: Throwable) : Exception(cause)