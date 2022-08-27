package org.nordicthings.inventory.item.jpa

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories("org.nordicthings.inventory.item.jpa.repository")
@EntityScan("org.nordicthings.inventory.item.jpa.entity")
class JpaConfig {
}
