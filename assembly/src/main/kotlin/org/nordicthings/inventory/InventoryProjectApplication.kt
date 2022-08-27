package org.nordicthings.inventory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InventoryProjectApplication

fun main(args: Array<String>) {
	runApplication<InventoryProjectApplication>(*args)
}
