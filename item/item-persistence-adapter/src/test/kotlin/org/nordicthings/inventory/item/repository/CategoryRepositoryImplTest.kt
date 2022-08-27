package org.nordicthings.inventory.item.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import org.nordicthings.inventory.item.app.persistence.CategoryRepository
import org.nordicthings.inventory.item.domain.model.Category
import org.nordicthings.inventory.item.domain.model.Key
import org.nordicthings.inventory.item.app.persistence.EntityNotFoundException
import org.nordicthings.inventory.item.jpa.entity.CategoryEntity
import org.nordicthings.inventory.item.jpa.repository.CategoryJpaRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

@ExtendWith(MockitoExtension::class)
internal class CategoryRepositoryImplTest
{
    private val anyCategoryEntity = CategoryEntity(1, "anyCat","Any Category")
    private val anotherCategoryEntity = CategoryEntity(1, "anotherCat","Another Category")
    private val anyCategory = Category(1, Key("anyCat"),"Any Category")
    private val anotherCategory = Category(1, Key("anotherCat"),"Another Category")

    private val jpaRepository = mock<CategoryJpaRepository>()
    private val sut: CategoryRepository = CategoryRepositoryImpl(jpaRepository)

    @Test
    fun whenFindByKeyFindsDbEntryThenReturnValidCategory() {
        whenever(jpaRepository.findByKey(eq(anyCategory.key.value))).thenReturn(anyCategoryEntity)
        assertThat(sut.findByKey(anyCategory.key.value)).isEqualTo(anyCategory)
    }

    @Test
    fun whenFindByKeyFindsNothingThenRaiseException() {
        whenever(jpaRepository.findByKey(any())).thenReturn(null)
        assertThrows<EntityNotFoundException> { sut.findByKey("any") }
    }

    @Test
    fun whenFindByNameFindsDbEntriesThenReturnListOfCategories() {
        whenever(jpaRepository.findByNameIgnoreCaseLike(eq("%Category"), any())).thenReturn(listOf(anyCategoryEntity,anotherCategoryEntity))
        assertThat(sut.findByName("*Category", 0, 10)).containsExactlyInAnyOrder(anyCategory, anotherCategory)
    }

    @Test
    fun whenFindByNameFindsNothingThenReturnEmptyList() {
        whenever(jpaRepository.findByNameIgnoreCaseLike(any(),any())).thenReturn(listOf())
        assertThat(sut.findByName("notMatchingPattern",0,10)).isEmpty()
    }

    @Test
    fun whenFindAllFindsDbEntriesThenReturnListOfCategories() {
        whenever(jpaRepository.findAll( any<Pageable>())).thenReturn(PageImpl(listOf(anyCategoryEntity,anotherCategoryEntity)))
        assertThat(sut.findAll(0, 10)).containsExactlyInAnyOrder(anyCategory, anotherCategory)
    }

    @Test
    fun whenFindAllFindsNothingThenReturnEmptyList() {
        whenever(jpaRepository.findAll( any<Pageable>())).thenReturn(PageImpl(listOf()))
        assertThat(sut.findAll(0, 10)).isEmpty()
    }

    @Test
    fun whenSavedThenReturnSavedCategory() {
        val newCategoryEntity = CategoryEntity(null, "newCat","New Category")
        val savedCategoryEntity = CategoryEntity(1, "newCat","New Category")
        val newCategory = Category(null, Key("newCat"),"New Category")
        whenever(jpaRepository.save(newCategoryEntity)).thenReturn(savedCategoryEntity)
        val savedCategory = sut.save(newCategory)
        assertThat(sut.save(savedCategory)).isEqualTo(newCategory)
        assertThat(savedCategory.id).isEqualTo(savedCategoryEntity.id)
    }

    @Test
    fun whenDeleteCategoryThenAppropriateEntityIsPassed() {
        sut.delete(anyCategory)
        argumentCaptor<CategoryEntity>().apply {
            verify(jpaRepository).delete(capture())
            assertThat(firstValue).isEqualTo(anyCategoryEntity)
        }
    }

}
