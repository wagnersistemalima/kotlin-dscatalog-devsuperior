package br.com.wagner.dscatalog.products.unitario

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import br.com.wagner.dscatalog.product.model.Product
import br.com.wagner.dscatalog.product.repository.ProductRepository
import br.com.wagner.dscatalog.product.service.BuscarProductIdService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class BuscarProductIdServiceTest {

    @field:InjectMocks
    lateinit var buscaProductIdservice: BuscarProductIdService

    @field:Mock
    lateinit var productRepository: ProductRepository

    // 1 cenario de teste

    @Test
    fun `deve retornar objeto de resposta, quando solicitado o id de produto valido` () {

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

        val idProductValido = 5000L

        // ação

        //comportamento
        Mockito.`when`(productRepository.findById(idProductValido)).thenReturn(Optional.of(product01))

        // assertivas
        Assertions.assertDoesNotThrow { buscaProductIdservice.findById(idProductValido) }
    }

    // 2 cenario de teste

    @Test
    fun `deve lançar exception, quando o id do produto não existe cadastrado` () {

        // cenario

        val idProductInvalido = 5000L

        // ação

        //comportamento
        Mockito.`when`(productRepository.findById(idProductInvalido)).thenReturn(Optional.empty())

        // assertivas
        Assertions.assertThrows(ResourceNotFoundException::class.java) {buscaProductIdservice.findById(idProductInvalido)}
    }
}