package org.nordicthings.inventory.item.app.service

import org.nordicthings.inventory.item.app.persistence.ItemRepository
import org.nordicthings.inventory.item.domain.model.Item
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ItemService(val itemRepository: ItemRepository) {
    private val logger: Logger = LoggerFactory.getLogger(ItemService::class.java)

    fun save(item: Item): Item {
        val savedItem = itemRepository.save(item)
        logger.debug("Item {} saved to db", savedItem)
        return savedItem
    }
}
