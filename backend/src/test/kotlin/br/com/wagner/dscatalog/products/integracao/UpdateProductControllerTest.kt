package br.com.wagner.dscatalog.products.integracao

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.product.model.Product
import br.com.wagner.dscatalog.product.repository.ProductRepository
import br.com.wagner.dscatalog.product.request.UpdateProductRequest
import com.fasterxml.jackson.databind.ObjectMapper
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
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@ActiveProfiles("test")
@Transactional
class UpdateProductControllerTest {

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    @field:Autowired
    lateinit var productRepository: ProductRepository

    @field:Autowired
    lateinit var categoryRepository: CategoryRepository

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

    // 1 cenario de teste

    @Test
    fun `deve retornar 200, atualizar produto, nao lançar exception` () {

        // cenario

        val category = Category(
            name = "eletronics"
        )
        categoryRepository.save(category)

        val idCategoryValido = category.id

        val product01 = Product(
            name = "tv plasma",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            category = category
        )
        productRepository.save(product01)

        val idProductValido = product01.id

        val request = UpdateProductRequest(
            name = "Radio",
            description = "sony",
            price = 300.0,
            imgUrl = "http://image.jpg",
            idCategory = idCategoryValido!!
        )

        val uri = UriComponentsBuilder.fromUriString("/api/products/{id}").buildAndExpand(idProductValido).toUri()

        // ação
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        // assertivas
    }

    // 2 cenario de teste

    @Test
    fun `deve retornar 404, quando id do produto não existe` () {

        // cenario

        val category = Category(
            name = "eletronics"
        )
        categoryRepository.save(category)

        val idCategoryValido = category.id

        val product01 = Product(
            name = "tv plasma",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            category = category
        )
        productRepository.save(product01)

        val idProductNaoExiste = 5000L

        val request = UpdateProductRequest(
            name = "Radio",
            description = "sony",
            price = 300.0,
            imgUrl = "http://image.jpg",
            idCategory = idCategoryValido!!
        )

        val uri = UriComponentsBuilder.fromUriString("/api/products/{id}").buildAndExpand(idProductNaoExiste).toUri()

        // ação
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(404))

        // assertivas
    }

    // 3 cenario de teste

    @Test
    fun `deve retornar 404, quando id da categoria nao existe para atualizar produto` () {

        // cenario

        val category = Category(
            name = "eletronics"
        )
        categoryRepository.save(category)

        val idCategoryNaoExiste = 5000L

        val product01 = Product(
            name = "tv plasma",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            category = category
        )
        productRepository.save(product01)

        val idProductValido = product01.id

        val request = UpdateProductRequest(
            name = "Radio",
            description = "sony",
            price = 300.0,
            imgUrl = "http://image.jpg",
            idCategory = idCategoryNaoExiste
        )

        val uri = UriComponentsBuilder.fromUriString("/api/products/{id}").buildAndExpand(idProductValido).toUri()

        // ação
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(404))

        // assertivas
    }

    // 4 cenario de teste

    @Test
    fun `deve retornar 422, ao tentar atualizar produto com nome ja existente cadastrado` () {

        // cenario

        val category = Category(
            name = "eletronics"
        )
        categoryRepository.save(category)

        val idCategoryValido = category.id

        val product01 = Product(
            name = "tv plasma",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            category = category
        )
        productRepository.save(product01)

        val idProductValido = product01.id

        val request = UpdateProductRequest(
            name = "tv plasma",
            description = "sony",
            price = 300.0,
            imgUrl = "http://image.jpg",
            idCategory = idCategoryValido!!
        )

        val uri = UriComponentsBuilder.fromUriString("/api/products/{id}").buildAndExpand(idProductValido).toUri()

        // ação
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(422))

        // assertivas
    }

    // 5 cenario de teste

    @Test
    fun `deve retornar 400, ao tentar atualizar produto com nome vazio` () {

        // cenario

        val category = Category(
            name = "eletronics"
        )
        categoryRepository.save(category)

        val idCategoryValido = category.id

        val product01 = Product(
            name = "tv sony",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            category = category
        )
        productRepository.save(product01)

        val idProductValido = product01.id

        val request = UpdateProductRequest(
            name = "",
            description = "sony",
            price = 300.0,
            imgUrl = "http://image.jpg",
            idCategory = idCategoryValido!!
        )

        val uri = UriComponentsBuilder.fromUriString("/api/products/{id}").buildAndExpand(idProductValido).toUri()

        // ação
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas
    }

    // 6 cenario de teste

    @Test
    fun `deve retornar 400, ao tentar atualizar produto com descricao vazio` () {

        // cenario

        val category = Category(
            name = "eletronics"
        )
        categoryRepository.save(category)

        val idCategoryValido = category.id

        val product01 = Product(
            name = "tv sony",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            category = category
        )
        productRepository.save(product01)

        val idProductValido = product01.id

        val request = UpdateProductRequest(
            name = "Bola",
            description = "",
            price = 300.0,
            imgUrl = "http://image.jpg",
            idCategory = idCategoryValido!!
        )

        val uri = UriComponentsBuilder.fromUriString("/api/products/{id}").buildAndExpand(idProductValido).toUri()

        // ação
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas
    }

    // 7 cenario de teste

    @Test
    fun `deve retornar 400, ao tentar atualizar produto com preço zerado` () {

        // cenario

        val category = Category(
            name = "eletronics"
        )
        categoryRepository.save(category)

        val idCategoryValido = category.id

        val product01 = Product(
            name = "tv sony",
            description = "tv 29 plogedas",
            price = 1000.0,
            imgUrl = "http://image.jpg",
            category = category
        )
        productRepository.save(product01)

        val idProductValido = product01.id

        val request = UpdateProductRequest(
            name = "Bola",
            description = "azul anil",
            price = 0.0,
            imgUrl = "http://image.jpg",
            idCategory = idCategoryValido!!
        )

        val uri = UriComponentsBuilder.fromUriString("/api/products/{id}").buildAndExpand(idProductValido).toUri()

        // ação
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas
    }

    // metodo para desserializar objeto da request
    fun toJson(request: UpdateProductRequest): String {
        return objectMapper.writeValueAsString(request)
    }
}