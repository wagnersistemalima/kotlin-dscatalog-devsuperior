package br.com.wagner.dscatalog.category.repository

import br.com.wagner.dscatalog.category.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: JpaRepository<Category, Long> {

    fun existsByName(name: String): Boolean
}