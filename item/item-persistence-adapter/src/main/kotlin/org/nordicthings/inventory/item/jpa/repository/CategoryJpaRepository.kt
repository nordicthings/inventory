package org.nordicthings.inventory.item.jpa.repository

import org.nordicthings.inventory.item.jpa.entity.CategoryEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface CategoryJpaRepository: PagingAndSortingRepository<CategoryEntity, Long> {
    fun findByKey(key: String): CategoryEntity?
    fun findByNameIgnoreCaseLike(searchPattern: String, page: Pageable): List<CategoryEntity>
}
