package com.shudss00.gigachat.data.source.remote.common

interface ApiResponse {
    val code: String?
    val msg: String
    val result: String
}