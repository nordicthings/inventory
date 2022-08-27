package org.nordicthings.inventory.item.jpa.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.nordicthings.inventory.item.jpa.JpaConfig
import org.nordicthings.inventory.item.jpa.entity.CategoryEntity
import org.nordicthings.inventory.item.jpa.entity.ItemEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [JpaConfig::class])
@ActiveProfiles("jpatest")
@DataJpaTest
internal class ItemJpaRepositoryTest
@Autowired
constructor(
    val itemRepo: ItemJpaRepository,
    val categoryRepo: CategoryJpaRepository
) {
    val item1KnownInDb = ItemEntity(1, "anyItem1", "Any Item 1", CategoryEntity(null, "anyCat1", "Any Category 1"))
    val item2KnownInDb = ItemEntity(2, "anyItem2", "Any Item 2", CategoryEntity(null, "anyCat2", "Any Category 2"))
    val item3KnownInDb = ItemEntity(3, "anyItem3", "Any Item 3", null)

    @Test
    @Sql("classpath:populate-testdata.sql")
    fun whenNewItemWithNewCategorySavedThenBothAreInserted() {
        val savedItem =
            itemRepo.save(ItemEntity(null, "newItem", "New Item", CategoryEntity(null, "newCat", "New Category")))
        assertThat(savedItem.id).isNotNull
        assertThat(savedItem.category!!.id).isNotNull
    }

    @Test
    @Sql("classpath:populate-testdata.sql")
    fun whenNewItemWithExistingCategorySavedThenItemIsInserted() {
        val categoryKnownInDb = categoryRepo.findByKey("anyCat1")
        val savedItem = itemRepo.save(ItemEntity(null, "newItem", "New Item", categoryKnownInDb))
        assertThat(savedItem.id).isNotNull
        assertThat(savedItem.category!!.id).isEqualTo(categoryKnownInDb!!.id)
    }

    @Test
    @Sql("classpath:populate-testdata.sql")
    fun savingItemWithoutCategoryIsAllowed() {
        val savedItem = itemRepo.save(ItemEntity(key = "newItem", name = "New Item"))
        assertThat(savedItem.id).isNotNull
        assertThat(savedItem.category).isNull()
    }

    @Test
    @Sql("classpath:populate-testdata.sql")
    fun whenLoadingExistingItemFromDbThenCategoryIsPopulated() {
        val foundItem = itemRepo.findByKey(item1KnownInDb.key)
        assertThat(foundItem).isEqualTo(item1KnownInDb)
        assertThat(foundItem!!.category).isNotNull
    }

    @Test
    @Sql("classpath:populate-testdata.sql")
    fun whenLoadingWithSearchPatternThenFound() {
        assertThat(itemRepo.findByNameIgnoreCaseLike("%item%", Pageable.unpaged())).containsExactlyInAnyOrder(item1KnownInDb,item2KnownInDb,item3KnownInDb)
    }

}
