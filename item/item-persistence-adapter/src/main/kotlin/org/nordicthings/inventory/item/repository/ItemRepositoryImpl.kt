package org.nordicthings.inventory.item.repository

import org.nordicthings.inventory.item.app.persistence.EntityNotFoundException
import org.nordicthings.inventory.item.app.persistence.ItemRepository
import org.nordicthings.inventory.item.domain.model.Item
import org.nordicthings.inventory.item.jpa.entity.ItemEntity
import org.nordicthings.inventory.item.jpa.repository.ItemJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class ItemRepositoryImpl(val jpaRepository: ItemJpaRepository) : ItemRepository {

    override fun findByKey(key: String): Item {
        val entity = jpaRepository.findByKey(key)?: throw EntityNotFoundException("No item found for key='$key'")
        return entity.toDomain()
    }

    override fun findByName(pattern: String, offset: Int, size: Int): List<Item> {
        return jpaRepository.findByNameIgnoreCaseLike(pattern.replace('*','%'), PageRequest.of(offset, size)).map (ItemEntity::toDomain)
    }

    override fun findAll(offset: Int, size: Int): List<Item> = jpaRepository.findAll(PageRequest.of(offset, size)).map(ItemEntity::toDomain).toList()

    override fun save(item: Item): Item = jpaRepository.save(ItemEntity.fromDomain(item)).toDomain()

    override fun delete(item: Item) = jpaRepository.delete(ItemEntity.fromDomain(item))
}
