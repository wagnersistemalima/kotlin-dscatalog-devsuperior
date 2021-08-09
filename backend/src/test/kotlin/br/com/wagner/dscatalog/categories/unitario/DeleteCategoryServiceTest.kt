package br.com.wagner.dscatalog.categories.unitario

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.service.DeleteCategoryService
import br.com.wagner.dscatalog.handler.ExceptionGenericValidated
import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import br.com.wagner.dscatalog.product.model.Product
import br.com.wagner.dscatalog.product.repository.ProductRepository
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

    @field:Mock
    lateinit var productRepository: ProductRepository

    // 1 cenario de teste

    @Test
    fun `deve deletar uma categoria, e nao lançar exception, quando for passado id valido caregoria`() {

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

    // 3 cenario de teste

    @Test
    fun `deve lanaçar exception , quando tentar deletar categoria com produtos associado a ela`() {

        // cenario

        val category = Category(
            name = "Livros"
        )

        val categoryId = 5000L

        val product1 = Product(
            name = "Ventilador",
            description = "branco, turbo",
            price = 250.0,
            imgUrl = "http://imagem.jpg",
            category = category
        )

        val product2 = Product(
            name = "Ar condicionado",
            description = "branco, turbo",
            price = 900.0,
            imgUrl = "http://imagem.jpg",
            category = category
        )

        //ação

        val list:  MutableList<Product> = mutableListOf()
        list.add(product1)
        list.add(product2)

        //comportamento
        Mockito.`when`(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category))

        // comportamento, retorna uma lista com produtos associados a categoria que se quer deletar
        Mockito.`when`(productRepository.findByCategoryId(categoryId)).thenReturn(list)

        //assertiva

        //deve lançar exception
        Assertions.assertThrows(ExceptionGenericValidated::class.java) {deleteCategoryService.delete(categoryId)}

        // verifica se foi chamado delete()
        Mockito.verify(categoryRepository, Mockito.times(0)).delete(category)
    }
}