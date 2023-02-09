package com.shudss00.gigachat.domain.common

enum class OnlineStatus(val code: Int) {
    ACTIVE(0),
    IDLE(1),
    OFFLINE(2),
    INDEFINITE(3);

    companion object {
        fun from(code: Int): OnlineStatus {
            return OnlineStatus.values().find { it.code == code }
                ?: throw IllegalArgumentException("The code is not included in the range from 0 to 3")
        }

        fun from(title: String): OnlineStatus {
            return when(title) {
                "active" -> ACTIVE
                "idle" -> IDLE
                "offline" -> OFFLINE
                "" -> INDEFINITE
                else -> throw IllegalArgumentException("The title is not matched to any string")
            }
        }
    }
}