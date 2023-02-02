package com.shudss00.gigachat.presentation.streamlist.listitems

data class TopicItem(
    val topicTitle: String,
    val streamTitle: String,
    val messageCount: Int
) : StreamListItem