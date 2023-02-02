package com.shudss00.gigachat.presentation.streamlist.listitems

data class StreamItem(
    val id: Long,
    val title: String,
    val isExpanded: Boolean
) : StreamListItem