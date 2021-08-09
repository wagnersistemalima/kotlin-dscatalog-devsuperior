package br.com.wagner.dscatalog.products.unitario

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.handler.ExceptionGenericValidated
import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import br.com.wagner.dscatalog.product.model.Product
import br.com.wagner.dscatalog.product.repository.ProductRepository
import br.com.wagner.dscatalog.product.request.InsertProductRequest
import br.com.wagner.dscatalog.product.service.InsertProductService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class InsertPrductServiceTest {

    @field:InjectMocks
    lateinit var insertProductService: InsertProductService

    @field:Mock
    lateinit var categoryRepository: CategoryRepository

    @field:Mock
    lateinit var productRepository: ProductRepository

    // 1 cenario de teste

    @Test
    fun `deve inserir um novo produto, não lançar exception`() {

        // cenario

        val category = Category(
            name = "eletronics"
        )

        val idCategoriaExistente = 5000L

        val request = InsertProductRequest(
            name = "tv plasma",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            idCategory = idCategoriaExistente

        )

        val product = request.toModel(category)

        // ação

        // comportamento
        Mockito.`when`(categoryRepository.findById(request.idCategory)).thenReturn(Optional.of(category))
        Mockito.`when`(productRepository.findByName(request.name)).thenReturn(Optional.empty())

        // assertiva

        Assertions.assertDoesNotThrow { insertProductService.insert(request) }

        Mockito.verify(productRepository, Mockito.times(1)).save(product)

    }

    // 2 cenario de teste

    @Test
    fun `deve lançar exception quando tentar cadastrar produto com nome ja existente`() {

        // cenario

        val category = Category(
            name = "eletronics"
        )

        val idCategoriaExistente = 5000L

        val request = InsertProductRequest(
            name = "tv plasma",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            idCategory = idCategoriaExistente

        )

        val product = Product(
            name = "tv plasma",
            description = "tv 12 plogedas",
            price = 500.0,
            imgUrl = "http://image2.jpg",
            category = category
        )

        // ação

        // comportamento
        Mockito.`when`(categoryRepository.findById(request.idCategory)).thenReturn(Optional.of(category))
        Mockito.`when`(productRepository.findByName(request.name)).thenReturn(Optional.of(product))

        // assertiva

        Assertions.assertThrows(ExceptionGenericValidated::class.java) {insertProductService.insert(request)}
        // verifica se foi chamado o save()
        Mockito.verify(productRepository, Mockito.times(0)).save(product)

    }

    // 3 cenario de teste

    @Test
    fun `deve lançar exception quando tentar cadastrar produto associado a uma categoria inexistente`() {

        // cenario


        val idCategoriaInvalida = 5000L

        val request = InsertProductRequest(
            name = "tv plasma",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            idCategory = idCategoriaInvalida

        )

        // ação

        // comportamento
        Mockito.`when`(categoryRepository.findById(request.idCategory)).thenReturn(Optional.empty())
        Mockito.`when`(productRepository.findByName(request.name)).thenReturn(Optional.empty())

        // assertiva

        Assertions.assertThrows(ResourceNotFoundException::class.java) {insertProductService.insert(request)}

    }
}