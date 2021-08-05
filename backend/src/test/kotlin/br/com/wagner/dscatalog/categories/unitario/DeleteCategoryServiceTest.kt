package br.com.wagner.dscatalog.categories.unitario

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.service.DeleteCategoryService
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
class DeleteCategoryServiceTest {

    @field:InjectMocks
    lateinit var deleteCategoryService: DeleteCategoryService

    @field:Mock
    lateinit var categoryRepository: CategoryRepository

    // 1 cenario de teste

    @Test
    fun `deve deletar uma categoria, e nao lan~çar exception, quando for passado id valido caregoria`() {

        // cenario

        val category = Category(
            name = "Livros"
        )

        val idExistente = 1L

        //ação

        //comportamento
        Mockito.`when`(categoryRepository.findById(idExistente)).thenReturn(Optional.of(category))

        //assertiva

        //nao deve lançar exception
        Assertions.assertDoesNotThrow { deleteCategoryService.delete(idExistente) }

        // verifica se foi chamado delete()
        Mockito.verify(categoryRepository, Mockito.times(1)).delete(category)
    }

    // 2 cenario de teste

    @Test
    fun `deve lanaçar exception, quando tentar deletar categoria com id inexistente`() {

        // cenario

        val category = Category(
            name = "Livros"
        )

        val idInexistente = 5000L

        //ação

        //comportamento
        Mockito.`when`(categoryRepository.findById(idInexistente)).thenReturn(Optional.empty())

        //assertiva

        //deve lançar exception
        Assertions.assertThrows(ResourceNotFoundException::class.java) {deleteCategoryService.delete(idInexistente)}

        // verifica se foi chamado delete()
        Mockito.verify(categoryRepository, Mockito.times(0)).delete(category)
    }
}