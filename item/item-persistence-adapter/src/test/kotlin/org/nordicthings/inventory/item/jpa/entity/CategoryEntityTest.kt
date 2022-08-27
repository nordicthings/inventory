package org.nordicthings.inventory.item.jpa.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.nordicthings.inventory.commons.test.DomainTestHelper
import org.nordicthings.inventory.item.domain.model.Category
import org.nordicthings.inventory.item.domain.model.Key

internal class CategoryEntityTest {

    private val orgCategory = CategoryEntity(null,"any1", "anyName1")
    private val equalCategory = CategoryEntity(null,"any1", "AnyName1")
    private val anotherEqualCategory = CategoryEntity(null, "any1", "any Name 1")
    private val notEqualCategory = CategoryEntity(null, "another", "anotherName")
    private val precedingCategory = CategoryEntity(null, "any0", "anyName0")
    private val followingCategory = CategoryEntity(null, "any2", "anyName2")

    private val orgDomainCategory = Category(null, Key("any1"), "anyName1")

    private val helper = DomainTestHelper()

    @Test
    fun checkHashCodeEquals() {
        helper.checkEqualsContract(orgCategory, equalCategory, anotherEqualCategory, notEqualCategory)
        helper.checkHashCodeContract(orgCategory, equalCategory)
    }

    @Test
    fun checkComparable() {
        helper.checkComparableToContract(orgCategory, equalCategory, precedingCategory, followingCategory)
        helper.checkComparableToConsistence(orgCategory, equalCategory)
    }

    @Test
    fun whenDomainFromEntityThenValidEntity() {
        assertThat(CategoryEntity.fromDomain(orgDomainCategory)).isEqualTo(orgCategory)
    }

    @Test
    fun whenEntityToDomainThenValidDomain() {
        assertThat(orgCategory.toDomain()).isEqualTo(orgDomainCategory)
    }

}
