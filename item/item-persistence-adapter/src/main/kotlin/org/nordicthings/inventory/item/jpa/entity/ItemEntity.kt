package org.nordicthings.inventory.item.jpa.entity

import org.apache.commons.lang3.builder.CompareToBuilder
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.nordicthings.inventory.item.domain.model.Item
import org.nordicthings.inventory.item.domain.model.Key
import javax.persistence.*

@Entity
@Table(name = "items")
class ItemEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ItemSeq")
    @SequenceGenerator(name = "ItemSeq", sequenceName = "item_seq", allocationSize = 1)
    val id: Long? = null,

    @Column(name = "ikey")
    val key: String,

    @Column(name = "iname")
    val name: String,

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "category_ref")
    var category: CategoryEntity? = null
) : Comparable<ItemEntity> {
    companion object {
        fun fromDomain(item: Item): ItemEntity = ItemEntity(
            item.id,
            item.key.value,
            item.name,
            if (item.category == null) null else CategoryEntity.fromDomain(item.category!!)
        )
    }

    override fun equals(other: Any?): Boolean =
        other != null && EqualsBuilder().append(key, (other as ItemEntity).key).isEquals

    override fun hashCode(): Int = HashCodeBuilder().append(key).toHashCode()

    override fun compareTo(other: ItemEntity): Int = CompareToBuilder().append(key, other.key).toComparison()

    override fun toString(): String = arrayOf(id, key, name).joinToString(",", this.javaClass.simpleName + "(", ")")

    fun toDomain(): Item = Item(id, Key(key), name, if (category == null) null else category!!.toDomain())
}
