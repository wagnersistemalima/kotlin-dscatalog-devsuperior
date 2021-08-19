package br.com.wagner.dscatalog.products.integracao

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.product.model.Product
import br.com.wagner.dscatalog.product.repository.ProductRepository
import br.com.wagner.dscatalog.util.TokenUtil
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class DeleteProductControllerTest {

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var categoryRepository: CategoryRepository

    @field:Autowired
    lateinit var productRepository: ProductRepository

    @field:Autowired
    lateinit var tokenUtil: TokenUtil

    // rodar antes de cada teste
    @BeforeEach
    internal fun setUp() {
        productRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    // rodar depois de cada teste
    @AfterEach
    internal fun tearDown() {
        productRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    // 1 cenario de test

    @Test
    fun `deve retornar 204 no content, quando solicitado a deleção do produto com id valido`() {

        val clientUsername = "maria@gmail.com";
        val clientPassword = "123456";

        val accesToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword)

        // cenario

        val category = Category(
            name = "eletronics"
        )
        categoryRepository.save(category)

        val product01 = Product(
            name = "tv plasma",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            category = category
        )
        productRepository.save(product01)

        val idProductValido = product01.id

        val uri = UriComponentsBuilder.fromUriString("/api/products/{id}").buildAndExpand(idProductValido).toUri()

        // ação

        mockMvc.perform(MockMvcRequestBuilders.delete(uri).header("Authorization", "Bearer $accesToken")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(204))

        // assertivas
    }

    // 2 cenario de test

    @Test
    fun `deve retornar 404, quando tentar deletar um produto com id não existente`() {

        val clientUsername = "maria@gmail.com";
        val clientPassword = "123456";

        val accesToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword)

        // cenario

        val category = Category(
            name = "eletronics"
        )
        categoryRepository.save(category)

        val product01 = Product(
            name = "tv plasma",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            category = category
        )
        productRepository.save(product01)

        val idProductNaoExiste = 5000L

        val uri = UriComponentsBuilder.fromUriString("/api/products/{id}").buildAndExpand(idProductNaoExiste).toUri()

        // ação

        mockMvc.perform(MockMvcRequestBuilders.delete(uri).header("Authorization", "Bearer $accesToken")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(404))

        // assertivas
    }
}