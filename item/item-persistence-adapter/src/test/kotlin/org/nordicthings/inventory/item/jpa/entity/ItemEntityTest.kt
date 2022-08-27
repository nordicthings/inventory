package org.nordicthings.inventory.item.jpa.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.nordicthings.inventory.commons.test.DomainTestHelper
import org.nordicthings.inventory.item.domain.model.Category
import org.nordicthings.inventory.item.domain.model.Item
import org.nordicthings.inventory.item.domain.model.Key

internal class ItemEntityTest {

    private val anyCategory = CategoryEntity(null,"any", "anyName")

    private val orgItem = ItemEntity(null, "any1", "anyName1", anyCategory)
    private val equalItem = ItemEntity(null, "any1", "AnyName1", anyCategory)
    private val anotherEqualItem = ItemEntity(null, "any1", "any Name 1", anyCategory)
    private val notEqualItem = ItemEntity(null, "another", "anotherName", anyCategory)
    private val precedingItem = ItemEntity(null, "any0", "anyName0", anyCategory)
    private val followingItem = ItemEntity(null, "any2", "anyName2", anyCategory)

    private val helper = DomainTestHelper()

    @Test
    fun checkHashCodeEquals() {
        helper.checkEqualsContract(orgItem, equalItem, anotherEqualItem, notEqualItem)
        helper.checkHashCodeContract(orgItem, equalItem)
    }

    @Test
    fun checkComparable() {
        helper.checkComparableToContract(orgItem, equalItem, precedingItem, followingItem)
        helper.checkComparableToConsistence(orgItem, equalItem)
    }

    @Test
    fun whenEntityFromDomainThenValidEntity() {
        val item = Item(4711, Key("anyItem"), "Any Item", Category(1234, Key("anyCategory"),"Any Category"))
        val entity = ItemEntity.fromDomain(item)
        assertThat(item.id).isEqualTo(entity.id)
        assertThat(item.key.value).isEqualTo(entity.key)
        assertThat(item.name).isEqualTo(entity.name)
        assertThat(item.category).isEqualTo(entity.category!!.toDomain())
    }

    @Test
    fun whenEntityFromDomainWithoutCategoryThenValidEntityWithoutCategory() {
        val item = Item(4711, Key("anyItem"), "Any Item")
        val entity = ItemEntity.fromDomain(item)
        assertThat(entity.category).isNull()
    }

    @Test
    fun whenEntityToDomainThanValidItem() {
        val entity = ItemEntity(4711, "anyItem", "Any Item", CategoryEntity(1234, "anyCategory", "Any Category"))
        val item = entity.toDomain()
        assertThat(item.id).isEqualTo(entity.id)
        assertThat(item.key.value).isEqualTo(entity.key)
        assertThat(item.name).isEqualTo(entity.name)
        assertThat(item.category).isEqualTo(entity.category!!.toDomain())
    }

    @Test
    fun whenEntityToDomainWithMissingCategoryThenItemWithoutCategory() {
        val entity = ItemEntity(4711, "anyItem", "Any Item")
        val item = entity.toDomain()
        assertThat(item.category).isNull()
    }

}
