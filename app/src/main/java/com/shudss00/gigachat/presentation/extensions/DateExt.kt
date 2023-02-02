package com.shudss00.gigachat.presentation.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("ConstantLocale")
private val dateParser = SimpleDateFormat("d MMM", Locale.getDefault())

fun formatTimestamp(timestamp: Long): String = dateParser.format(timestamp * 1000)