package br.com.wagner.dscatalog.categories.unitario

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.response.BuscarCategoryIdResponse
import br.com.wagner.dscatalog.category.service.BuscarCategoryIdService
import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class BuscarCategoryIdServiceTest {

    @field:InjectMocks
    lateinit var buscarCategoryIdService: BuscarCategoryIdService

    @field:Mock
    lateinit var categoryRepository: CategoryRepository

    // 1 cenario de teste

    @Test
    fun `deve retornar um objeto de resposta, quando solicitado a busca por id da categoria valido e nao lançar exception`() {

        // cenario

        val idCategoryValido = 1L

        val category = Category(
            name = "Filme"
        )

        // ação

        val response = BuscarCategoryIdResponse(category)

        //comportamento
        Mockito.`when`(categoryRepository.findById(idCategoryValido)).thenReturn(Optional.of(category))

        // assertivas

        // nao deve lançar exception
        Assertions.assertDoesNotThrow { buscarCategoryIdService.findById(idCategoryValido) }

        Assertions.assertEquals(response, buscarCategoryIdService.findById(idCategoryValido))

    }

    // 2 cenario de teste

    @Test
    fun `deve lançar exception, quando id da categoria não existir`() {

        // cenario

        val idCategoryInvalido = 5000L

        // ação

        //comportamento
        Mockito.`when`(categoryRepository.findById(idCategoryInvalido)).thenReturn(Optional.empty())

        // assertivas

        // deve lançar exception
        Assertions.assertThrows(ResourceNotFoundException::class.java) {buscarCategoryIdService.findById(idCategoryInvalido)}

    }
}