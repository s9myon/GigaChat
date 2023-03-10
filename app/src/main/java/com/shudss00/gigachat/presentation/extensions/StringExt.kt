package com.shudss00.gigachat.presentation.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned

fun String.fromHtml(): Spanned = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ->
        Html.fromHtml(this.trim(), Html.FROM_HTML_MODE_COMPACT)
    else -> @Suppress("DEPRECATION") Html.fromHtml(this.trim())
}