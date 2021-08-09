package br.com.wagner.dscatalog.categories.unitario

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.request.InsertCategoryRequest
import br.com.wagner.dscatalog.category.service.InsertCategoryService
import br.com.wagner.dscatalog.handler.ExceptionGenericValidated
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class InsertCategoryServiceTest {

    @field:InjectMocks
    lateinit var insertCategoryService: InsertCategoryService

    @field:Mock
    lateinit var categoryRepository: CategoryRepository

    // 1 cenario de teste

    @Test
    fun `deve cadastrar uma categoria, nao lançar exception`() {

        // cenario

        val request = InsertCategoryRequest(
            name = "Limpeza"
        )

        val category = request.toModel()

        // ação

        //comportamento
        Mockito.`when`(categoryRepository.existsByName(request.name)).thenReturn(false)
        //comportamento
        Mockito.`when`(categoryRepository.save(category)).thenReturn(category)

        // assertiva

        // nao deve lançar exception
        Assertions.assertDoesNotThrow { insertCategoryService.insert(request) }

        // verifica se o save() foi chamado
        Mockito.verify(categoryRepository, Mockito.times(1)).save(category)

    }

    // 2 cenario de teste

    @Test
    fun `nao deve cadastrar uma categoria quando a tentar inserir categoria de nome  existente, deve lançar exception`() {

        // cenario

        val category = Category(
            name = "Pneus"
        )

        val request = InsertCategoryRequest(
            name = "pneus"
        )

        // ação

        //comportamento
        Mockito.`when`(categoryRepository.existsByName(request.name.toLowerCase())).thenReturn(true)

        // assertiva

        //deve lançar exception
        Assertions.assertThrows(ExceptionGenericValidated::class.java) {insertCategoryService.insert(request)}

        // verifica se o save() foi chamado
        Mockito.verify(categoryRepository, Mockito.times(0)).save(category)

    }
}