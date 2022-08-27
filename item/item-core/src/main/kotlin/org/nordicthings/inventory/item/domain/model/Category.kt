package org.nordicthings.inventory.item.domain.model

import org.apache.commons.lang3.builder.CompareToBuilder
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder

data class Category(var id: Long?, val key: Key = Key(), val name: String) : Comparable<Category> {

    override fun hashCode() = HashCodeBuilder().append(key).toHashCode()

    override fun equals(other: Any?): Boolean = other != null && EqualsBuilder().append(this.key, (other as Category).key).isEquals

    override fun compareTo(other: Category): Int = CompareToBuilder().append(this.key, other.key).toComparison()

}
