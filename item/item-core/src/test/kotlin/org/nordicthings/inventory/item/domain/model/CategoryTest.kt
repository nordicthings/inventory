package org.nordicthings.inventory.item.domain.model

import org.junit.jupiter.api.Test
import org.nordicthings.inventory.commons.test.DomainTestHelper

internal class CategoryTest {
    private val orgCategory = Category(null, Key("any1"), "anyName1")
    private val equalCategory = Category(null, Key("any1"), "AnyName1")
    private val anotherEqualCategory = Category(null, Key("any1"), "any Name 1")
    private val notEqualCategory = Category(null, Key("another"), "anotherName")
    private val precedingCategory = Category(null, Key("any0"), "anyName0")
    private val followingCategory = Category(null, Key("any2"), "anyName2")

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

}
