package com.shudss00.gigachat.data.source.remote.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NarrowBuilder {

    @Suppress("unused")
    @Serializable
    private class Criteria(
        @SerialName("negated")
        val negated: Boolean = false,
        @SerialName("operator")
        val operator: String,
        @SerialName("operand")
        val operand: String
    )

    private val criterias = mutableListOf<Criteria>()

    fun build(): String {
        return Json.encodeToString(criterias)
    }

    fun stream(title: String, negated: Boolean = false): NarrowBuilder {
        criterias.add(
            Criteria(
                operator = "stream",
                operand = title,
                negated = negated
            )
        )
        return this
    }

    fun topic(title: String, negated: Boolean = false): NarrowBuilder {
        criterias.add(
            Criteria(
                operator = "topic",
                operand = title,
                negated = negated
            )
        )
        return this
    }

    fun privateMessagesWith(userId: Long, negated: Boolean = false): NarrowBuilder {
        criterias.add(
            Criteria(
                operator = "pm-with",
                operand = userId.toString(),
                negated = negated
            )
        )
        return this
    }
}