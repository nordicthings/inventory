package org.nordicthings.inventory.item.jpa.repository

import org.nordicthings.inventory.item.jpa.entity.ItemEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface ItemJpaRepository: PagingAndSortingRepository<ItemEntity, Long> {
    fun findByKey(key: String): ItemEntity?
    fun findByNameIgnoreCaseLike(searchPattern: String, page: Pageable): List<ItemEntity>
}
