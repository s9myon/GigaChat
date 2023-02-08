package com.shudss00.gigachat.data.source.remote.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NarrowBuilder {

    @Serializable
    private sealed interface Criteria {
        @Suppress("unused")
        @Serializable
        class StringOperandCriteria(
            @SerialName("negated")
            val negated: Boolean = false,
            @SerialName("operator")
            val operator: String,
            @SerialName("operand")
            val operand: String
        ) : Criteria

        @Suppress("unused")
        @Serializable
        class LongArrayOperandCriteria(
            @SerialName("negated")
            val negated: Boolean = false,
            @SerialName("operator")
            val operator: String,
            @SerialName("operand")
            val operand: LongArray
        ) : Criteria
    }

    private val criterias = mutableListOf<Criteria>()

    fun build(): String {
        return Json.encodeToString(criterias)
    }

    fun stream(title: String, negated: Boolean = false): NarrowBuilder {
        criterias.add(
            Criteria.StringOperandCriteria(
                operator = "stream",
                operand = title,
                negated = negated
            )
        )
        return this
    }

    fun topic(title: String, negated: Boolean = false): NarrowBuilder {
        criterias.add(
            Criteria.StringOperandCriteria(
                operator = "topic",
                operand = title,
                negated = negated
            )
        )
        return this
    }

    fun privateMessagesWith(vararg userIds: Long, negated: Boolean = false): NarrowBuilder {
        criterias.add(
            Criteria.LongArrayOperandCriteria(
                operator = "pm-with",
                operand = userIds,
                negated = negated
            )
        )
        return this
    }
}