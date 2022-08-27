package org.nordicthings.inventory.item.jpa.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.nordicthings.inventory.item.jpa.JpaConfig
import org.nordicthings.inventory.item.jpa.entity.CategoryEntity
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
internal class CategoryJpaRepositoryTest
@Autowired
constructor(
    val categoryRepo: CategoryJpaRepository
) {
    val category1KnownInDb = CategoryEntity(1, "anyCat1", "Any Category 1")
    val category2KnownInDb = CategoryEntity(2, "anyCat2", "Any Category 2")

    @Test
    fun whenSavedNewEntryThenPopulateId() {
        val categorySaved = categoryRepo.save(CategoryEntity(null, "anyNew", "Any new Category"))
        val categoryFound = categoryRepo.findByKey(categorySaved.key)
        assertThat(categoryFound).isNotNull
        assertThat(categoryFound).isEqualTo(categorySaved)
        assertThat(categoryFound!!.id).isNotNull
    }

    @Test
    @Sql("classpath:populate-testdata.sql")
    fun whenLoadingByKeyThenFound() {
        val foundCategory = categoryRepo.findByKey(category1KnownInDb.key)
        assertThat(foundCategory).isEqualTo(category1KnownInDb)
    }

    @Test
    @Sql("classpath:populate-testdata.sql")
    fun whenLoadingWithSearchPatternThenFound() {
        assertThat(categoryRepo.findByNameIgnoreCaseLike("%category%", Pageable.unpaged())).containsExactlyInAnyOrder(category1KnownInDb,category2KnownInDb)
    }

}
