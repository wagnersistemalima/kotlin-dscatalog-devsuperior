package br.com.wagner.dscatalog.product.repository

import br.com.wagner.dscatalog.product.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository: JpaRepository<Product, Long> {


    fun findByName(name: String): Optional<Product>

    fun findByCategoryId(id: Long): MutableList<Product>

    fun existsByName(name: String): Boolean
}