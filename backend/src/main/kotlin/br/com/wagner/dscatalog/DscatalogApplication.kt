package br.com.wagner.dscatalog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DscatalogApplication

fun main(args: Array<String>) {
	runApplication<DscatalogApplication>(*args)
}
