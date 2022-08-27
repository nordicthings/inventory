package org.nordicthings.inventory.item.domain.model

import org.apache.commons.lang3.builder.CompareToBuilder
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder

data class Item(
    var id: Long?,
    val key: Key,
    val name: String,
    var category: Category? = null) : Comparable<Item> {

    override fun equals(other: Any?) = other != null && EqualsBuilder().append(key, (other as Item).key).isEquals

    override fun hashCode() = HashCodeBuilder().append(key).toHashCode()

    override fun compareTo(other: Item) = CompareToBuilder().append(key, other.key).toComparison()

}
