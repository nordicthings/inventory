package org.nordicthings.inventory.item.domain.model

import org.apache.commons.lang3.builder.CompareToBuilder
import java.util.*

data class Key(val value: String = UUID.randomUUID().toString()): Comparable<Key> {
    override fun compareTo(other: Key): Int = CompareToBuilder().append(this.value, other.value).toComparison()
    override fun toString(): String = "'$value'"
}
