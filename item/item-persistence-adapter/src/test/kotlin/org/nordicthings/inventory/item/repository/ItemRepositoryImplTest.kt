package org.nordicthings.inventory.item.repository

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import org.nordicthings.inventory.item.app.persistence.EntityNotFoundException
import org.nordicthings.inventory.item.app.persistence.ItemRepository
import org.nordicthings.inventory.item.domain.model.Category
import org.nordicthings.inventory.item.domain.model.Item
import org.nordicthings.inventory.item.domain.model.Key
import org.nordicthings.inventory.item.jpa.entity.CategoryEntity
import org.nordicthings.inventory.item.jpa.entity.ItemEntity
import org.nordicthings.inventory.item.jpa.repository.ItemJpaRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

internal class ItemRepositoryImplTest {

    private val anyCategoryEntity = CategoryEntity(1, "anyCategory","Any Category")

    private val anyItemEntity = ItemEntity(1, "anyItem1", "Any Item 1", anyCategoryEntity)
    private val anotherItemEntity = ItemEntity(2, "anyItem2", "Any Item 2", anyCategoryEntity)

    private val anyCategory = Category(1, Key("anyCategory"),"Any Category")
    private val anyItem = Item(1, Key("anyItem1"),"Any Item 1", anyCategory)
    private val anotherItem = Item(2, Key("anyItem2"),"Any Item 2", anyCategory)

    private val jpaRepository = mock<ItemJpaRepository>()
    private val sut: ItemRepository = ItemRepositoryImpl(jpaRepository)

    @Test
    fun whenFindByKeyFindsDbEntryThenReturnValidItem() {
        whenever(jpaRepository.findByKey(eq(anyItem.key.value))).thenReturn(anyItemEntity)
        Assertions.assertThat(sut.findByKey(anyItem.key.value)).isEqualTo(anyItem)
    }

    @Test
    fun whenFindByKeyFindsNothingThenRaiseException() {
        whenever(jpaRepository.findByKey(any())).thenReturn(null)
        assertThrows<EntityNotFoundException> { sut.findByKey("unknownItemKey") }
    }

    @Test
    fun whenFindByNameFindsDbEntriesThenReturnListOfItems() {
        whenever(jpaRepository.findByNameIgnoreCaseLike(eq("Item%"), any())).thenReturn(listOf(anyItemEntity,anotherItemEntity))
        Assertions.assertThat(sut.findByName("Item*", 0, 10)).containsExactlyInAnyOrder(anyItem, anotherItem)
    }

    @Test
    fun whenFindByNameFindsNothingThenReturnEmptyList() {
        whenever(jpaRepository.findByNameIgnoreCaseLike(any(),any())).thenReturn(listOf())
        Assertions.assertThat(sut.findByName("notMatchingPattern", 0, 10)).isEmpty()
    }

    @Test
    fun whenFindAllFindsDbEntriesThenReturnListOfCategories() {
        whenever(jpaRepository.findAll( any<Pageable>())).thenReturn(PageImpl(listOf(anyItemEntity,anotherItemEntity)))
        Assertions.assertThat(sut.findAll(0, 10)).containsExactlyInAnyOrder(anyItem, anotherItem)
    }

    @Test
    fun whenFindAllFindsNothingThenReturnEmptyList() {
        whenever(jpaRepository.findAll( any<Pageable>())).thenReturn(PageImpl(listOf()))
        Assertions.assertThat(sut.findAll(0, 10)).isEmpty()
    }

    @Test
    fun whenSavedThenReturnSavedItem() {
        val anyCategoryEntity = CategoryEntity(1, "anyCategory","Any Category")
        val newItemEntity = ItemEntity(null, "newItem", "new Item", anyCategoryEntity)
        val savedItemEntity = ItemEntity(1, "newItem", "new Item", anyCategoryEntity)
        val newItem = Item(null, Key("newItem"),"New Item")
        whenever(jpaRepository.save(newItemEntity)).thenReturn(savedItemEntity)
        val savedItem = sut.save(newItem)
        Assertions.assertThat(sut.save(savedItem)).isEqualTo(newItem)
        Assertions.assertThat(savedItem.id).isEqualTo(savedItemEntity.id)
    }

    @Test
    fun whenDeleteItemThenAppropriateEntityIsPassed() {
        sut.delete(anyItem)
        argumentCaptor<ItemEntity>().apply {
            verify(jpaRepository).delete(capture())
            Assertions.assertThat(firstValue).isEqualTo(anyItemEntity)
        }
    }

}
