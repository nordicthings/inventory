package org.nordicthings.inventory.item.jpa.entity

import liquibase.repackaged.org.apache.commons.lang3.builder.EqualsBuilder
import liquibase.repackaged.org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.CompareToBuilder
import org.nordicthings.inventory.item.domain.model.Category
import org.nordicthings.inventory.item.domain.model.Key
import javax.persistence.*

@Entity
@Table(name = "categories")
class CategoryEntity(
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CategorySeq")
    @SequenceGenerator(name="CategorySeq", sequenceName="category_seq", allocationSize=1)
    val id: Long?,

    @Column(name = "ckey")
    val key: String,

    @Column(name = "cname")
    val name: String
) : Comparable<CategoryEntity> {

    companion object {
        fun fromDomain(category: Category): CategoryEntity = CategoryEntity(category.id, category.key.value, category.name)
    }

    override fun equals(other: Any?): Boolean =
        other != null && EqualsBuilder().append(key, (other as CategoryEntity).key).isEquals

    override fun hashCode(): Int = HashCodeBuilder().append(key).toHashCode()

    override fun compareTo(other: CategoryEntity): Int = CompareToBuilder().append(key, other.key).toComparison()

    override fun toString(): String = arrayOf(id, key, name).joinToString(",", this.javaClass.simpleName + "(", ")")

    fun toDomain(): Category = Category(id, Key(key), name)

}
