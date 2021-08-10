package br.com.wagner.dscatalog.products.unitario

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import br.com.wagner.dscatalog.product.model.Product
import br.com.wagner.dscatalog.product.repository.ProductRepository
import br.com.wagner.dscatalog.product.service.DeleteProductService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class DeleteProductServiceTest {

    @field:InjectMocks
    lateinit var deleteProductService: DeleteProductService

    @field:Mock
    lateinit var productRepository: ProductRepository

    // 1 cenario de teste

    @Test
    fun `deve deletar um produto, quando solicitado a deleção com id do produto valido`() {

        // cenario

        val category = Category(
            name = "eletronics"
        )

        val product01 = Product(
            name = "tv plasma",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            category = category
        )

        val idProductValido = 1L

        // ação

        //comportamento
        Mockito.`when`(productRepository.findById(idProductValido)).thenReturn(Optional.of(product01))

        // assertivas

        Assertions.assertDoesNotThrow { deleteProductService.delete(idProductValido) }

        // verifica se o delete foi chamado
        Mockito.verify(productRepository, Mockito.times(1)).delete(product01)

    }

    // 2 cenario de teste

    @Test
    fun `deve lançar exception, ao tentar deletar produto com id nao existe`() {

        // cenario

        val category = Category(
            name = "eletronics"
        )

        val product01 = Product(
            name = "tv plasma",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            category = category
        )

        val idProductNaoExiste = 5000L

        // ação

        //comportamento
        Mockito.`when`(productRepository.findById(idProductNaoExiste)).thenReturn(Optional.empty())

        // assertivas

        Assertions.assertThrows(ResourceNotFoundException::class.java) {deleteProductService.delete(idProductNaoExiste)}

        // verifica se o delete foi chamado
        Mockito.verify(productRepository, Mockito.times(0)).delete(product01)

    }
}