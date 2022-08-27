package org.nordicthings.inventory.item.repository

import org.nordicthings.inventory.item.app.persistence.CategoryRepository
import org.nordicthings.inventory.item.domain.model.Category
import org.nordicthings.inventory.item.app.persistence.EntityNotFoundException
import org.nordicthings.inventory.item.jpa.entity.CategoryEntity
import org.nordicthings.inventory.item.jpa.repository.CategoryJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class CategoryRepositoryImpl(val jpaRepository: CategoryJpaRepository) : CategoryRepository {

    override fun findByKey(key: String): Category {
        val category = jpaRepository.findByKey(key) ?: throw EntityNotFoundException("No category found for key='$key'")
        return category.toDomain()
    }

    override fun findByName(pattern: String, offset: Int, size: Int): List<Category> =
        jpaRepository.findByNameIgnoreCaseLike(pattern.replace('*', '%'), PageRequest.of(offset, size))
            .map(CategoryEntity::toDomain)

    override fun findAll(offset: Int, size: Int): List<Category> =
        jpaRepository.findAll(PageRequest.of(offset, size)).map(CategoryEntity::toDomain).toList()

    override fun save(category: Category): Category = jpaRepository.save(CategoryEntity.fromDomain(category)).toDomain()

    override fun delete(category: Category) = jpaRepository.delete(CategoryEntity.fromDomain(category))
}
