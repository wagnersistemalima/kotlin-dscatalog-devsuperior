package br.com.wagner.dscatalog.categories.unitario

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.response.ListaCategoryResponse
import br.com.wagner.dscatalog.category.service.ListarCategoryService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.stream.Collectors

@ExtendWith(SpringExtension::class)
class ListarCategoryServiceTest {

    @field:InjectMocks
    lateinit var listarCategoryService: ListarCategoryService

    @field:Mock
    lateinit var categoryRepository: CategoryRepository

    // 1 cenario de teste

    @Test
    fun `deve retornar uma lista de objeto de resposta de categorias`() {

        // cenario

        val category1 = Category(
            name = "Eletronicos"
        )

        val category2 = Category(
            name = "Livros"
        )

        val list: MutableList<Category> = categoryRepository.findAll()

        val response = list.stream().map { category -> ListaCategoryResponse(category) }.collect(Collectors.toList())

        // ação

        //comportamento
        Mockito.`when`(categoryRepository.findAll()).thenReturn(list)

        // verifica se o findALL() foi chamado
        Mockito.verify(categoryRepository, Mockito.times(1)).findAll()

        // assertivas
        Assertions.assertEquals(response, listarCategoryService.findAll())
    }
}