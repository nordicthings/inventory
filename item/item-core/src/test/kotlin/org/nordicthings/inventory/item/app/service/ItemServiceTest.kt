package org.nordicthings.inventory.item.app.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.nordicthings.inventory.item.app.persistence.ItemRepository
import org.nordicthings.inventory.item.domain.model.Category
import org.nordicthings.inventory.item.domain.model.Item
import org.nordicthings.inventory.item.domain.model.Key

internal class ItemServiceTest {

    private val anyCategory = Category(1, Key("anyCategory"), "Any Category")
    private val anyItem = Item(null, Key("anyItem"), "Any Item", anyCategory)

    private val itemRepo = mock<ItemRepository>()
    private val sut = ItemService(itemRepo)

    @Test
    fun whenSaveThenReturnSavedItem() {
        val itemToSave = Item(1, Key("anyItem"), "Any Item", anyCategory)
        whenever(itemRepo.save(anyItem)).thenReturn(itemToSave)
        val savedItem = sut.save(itemToSave)
        assertThat(savedItem).isEqualTo(itemToSave)
        assertThat(savedItem.id).isNotNull
    }
}
