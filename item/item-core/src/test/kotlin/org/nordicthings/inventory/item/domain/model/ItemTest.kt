package org.nordicthings.inventory.item.domain.model

import org.junit.jupiter.api.Test
import org.nordicthings.inventory.commons.test.DomainTestHelper

class ItemTest() {
    private val orgItem = Item(null, Key("any1"), "anyName1", null)
    private val equalItem = Item(null, Key("any1"), "AnyName1", null)
    private val anotherEqualItem = Item(null, Key("any1"), "any Name 1", null)
    private val notEqualItem = Item(null, Key("another"), "anotherName", null)
    private val precedingItem = Item(null, Key("any0"), "anyName0", null)
    private val followingItem = Item(null, Key("any2"), "anyName2", null)

    private val helper: DomainTestHelper = DomainTestHelper()

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
}
