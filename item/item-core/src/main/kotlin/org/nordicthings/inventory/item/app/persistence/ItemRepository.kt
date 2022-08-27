package org.nordicthings.inventory.item.app.persistence

import org.nordicthings.inventory.item.domain.model.Item

interface ItemRepository {

    fun findByKey(key: String): Item
    fun findByName(pattern: String, offset: Int, size: Int): List<Item>
    fun findAll(offset: Int, size: Int): List<Item>
    fun save(item: Item): Item
    fun delete(item: Item)

}
