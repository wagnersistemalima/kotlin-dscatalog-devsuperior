package br.com.wagner.dscatalog.products.unitario

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.handler.ExceptionGenericValidated
import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import br.com.wagner.dscatalog.product.model.Product
import br.com.wagner.dscatalog.product.repository.ProductRepository
import br.com.wagner.dscatalog.product.request.UpdateProductRequest
import br.com.wagner.dscatalog.product.service.UpdateProductService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class UpdateProductServiceTest {

    @field:InjectMocks
    lateinit var updateProductService: UpdateProductService

    @field:Mock
    lateinit var productRepository: ProductRepository

    @field:Mock
    lateinit var categoryRepository: CategoryRepository

    // 1 cenario de teste

    @Test
    fun `deve atualizar product, quando dados validados, nao lançar exception`() {

        // cenario

        val category = Category(
            name = "eletronics"
        )
        val idCategoryValido = 4000L

        val product01 = Product(
            name = "tv sony",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            category = category
        )
        val idProductValido = 5000L

        val request = UpdateProductRequest(
            name = "Antena a cabo",
            description = "moderna",
            price = 150.0,
            imgUrl = "http://image.jpg",
            idCategory = idCategoryValido
        )

        // ação

        // comportamento
        Mockito.`when`(productRepository.findById(idProductValido)).thenReturn(Optional.of(product01))

        Mockito.`when`(categoryRepository.findById(idCategoryValido)).thenReturn(Optional.of(category))

        Mockito.`when`(productRepository.existsByName(request.name)).thenReturn(false)

        // assertivas
        Assertions.assertDoesNotThrow { updateProductService.update(idProductValido, request) }
    }

    // 2 cenario de teste

    @Test
    fun `deve lançar exception, quando id do produto nao existir`() {

        // cenario


        val idProductNaoExiste = 5000L

        val request = UpdateProductRequest(
            name = "Antena a cabo",
            description = "moderna",
            price = 150.0,
            imgUrl = "http://image.jpg",
            idCategory = 3000L
        )

        // ação

        // comportamento
        Mockito.`when`(productRepository.findById(idProductNaoExiste)).thenReturn(Optional.empty())

        // assertivas
        Assertions.assertThrows(ResourceNotFoundException::class.java) {updateProductService.update(idProductNaoExiste, request)}
    }

    // 3 cenario de teste

    @Test
    fun `deve lançar exception, quando id categoria não existir`() {

        // cenario

        val category = Category(
            name = "eletronics"
        )
        val idCategoryNaoExiste = 4000L

        val product01 = Product(
            name = "tv sony",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            category = category
        )
        val idProductValido = 5000L

        val request = UpdateProductRequest(
            name = "Antena a cabo",
            description = "moderna",
            price = 150.0,
            imgUrl = "http://image.jpg",
            idCategory = idCategoryNaoExiste
        )

        // ação

        // comportamento
        Mockito.`when`(productRepository.findById(idProductValido)).thenReturn(Optional.of(product01))

        Mockito.`when`(categoryRepository.findById(idCategoryNaoExiste)).thenReturn(Optional.empty())

        // assertivas
        Assertions.assertThrows(ResourceNotFoundException::class.java) {updateProductService.update(idProductValido, request )}
    }

    // 1 cenario de teste

    @Test
    fun `deve lançar exception, quando tentar atualizar produto com nome ja cadastrado`() {

        // cenario

        val category = Category(
            name = "eletronics"
        )
        val idCategoryValido = 4000L

        val product01 = Product(
            name = "tv sony",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            category = category
        )
        val idProductValido = 5000L

        val request = UpdateProductRequest(
            name = "tv sony",
            description = "moderna",
            price = 150.0,
            imgUrl = "http://image.jpg",
            idCategory = idCategoryValido
        )

        // ação

        // comportamento
        Mockito.`when`(productRepository.findById(idProductValido)).thenReturn(Optional.of(product01))

        Mockito.`when`(categoryRepository.findById(idCategoryValido)).thenReturn(Optional.of(category))

        Mockito.`when`(productRepository.existsByName(request.name)).thenReturn(true)

        // assertivas
        Assertions.assertThrows(ExceptionGenericValidated::class.java) {updateProductService.update(idProductValido, request)}
    }
}