package br.com.wagner.dscatalog.categories.unitario

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.request.UpdateCategoryRequest
import br.com.wagner.dscatalog.category.service.UpdateCategoryService
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
class UpdateCategoryServiceTest {

    @field:InjectMocks
    lateinit var updateCategoryService: UpdateCategoryService

    @field:Mock
    lateinit var categoryRepository: CategoryRepository

    // 1 cenario de teste

    @Test
    fun `deve atualizar uma categoria` () {

        // cenario

        val category = Category(
            name = "Eletronics"
        )
        val idCategoriaExistente = 1L

        val request = UpdateCategoryRequest(
            name = "Filme"
        )

        category.name = request.name

        // ação

        //comportamento
        Mockito.`when`(categoryRepository.findById(idCategoriaExistente)).thenReturn(Optional.of(category))
        Mockito.`when`(categoryRepository.save(category)).thenReturn(category)

        // assertivas

        // nao deve lançar exception
        Assertions.assertDoesNotThrow { updateCategoryService.update(idCategoriaExistente, request) }

        Assertions.assertEquals(category.name, request.name)

        // verifica se foi chamado o save
        Mockito.verify(categoryRepository, Mockito.times(1)).save(category)
    }

    // 2 cenario de teste

    @Test
    fun `deve lançar exception, ao tentar atualizar categoria de id inexistente` () {

        // cenario

        val idCategoriaInexistente = 5000L

        val request = UpdateCategoryRequest(
            name = "Filme"
        )

        // ação

        //comportamento
        Mockito.`when`(categoryRepository.findById(idCategoriaInexistente)).thenReturn(Optional.empty())

        // assertivas

        //deve lançar exception
        Assertions.assertThrows(ResourceNotFoundException::class.java) {updateCategoryService.update(idCategoriaInexistente, request)}

    }


}