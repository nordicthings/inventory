package org.nordicthings.inventory.item.app.persistence

import org.nordicthings.inventory.item.domain.model.Category

interface CategoryRepository {

    fun findByKey(key: String): Category?
    fun findByName(pattern: String, offset: Int, size: Int): List<Category>
    fun findAll(offset: Int, size: Int): List<Category>
    fun save(category: Category): Category
    fun delete(category: Category)

}
