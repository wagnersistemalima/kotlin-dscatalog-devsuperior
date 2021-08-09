package br.com.wagner.dscatalog.products.integracao

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.product.repository.ProductRepository
import br.com.wagner.dscatalog.product.request.InsertProductRequest
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
import java.net.URI

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@ActiveProfiles("test")
@Transactional
class InsertProductControllerTest {

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
    fun `deve retornar 201, ao enviar os dados validados para inserir um produto`() {

        // cenario

        val category = Category(
            name = "Eletronicos"
        )
        categoryRepository.save(category)

        val idCategoryValido: Long? = category.id

        val uri = URI("/api/products")

        val request = InsertProductRequest(
            name = "Ventilador",
            description = "Ventilador branco turbo",
            price = 250.0,
            imgUrl = "imagem.jpg",
            idCategory = idCategoryValido!!
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(201))

        // assertivas
    }

    // 2 cenario de teste

    @Test
    fun `deve retornar 400, ao enviar o nome produto vazio`() {

        // cenario

        val category = Category(
            name = "Eletronicos"
        )
        categoryRepository.save(category)

        val idCategoryValido: Long? = category.id

        val uri = URI("/api/products")

        val request = InsertProductRequest(
            name = "",
            description = "Ventilador branco turbo",
            price = 250.0,
            imgUrl = "imagem.jpg",
            idCategory = idCategoryValido!!
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas
    }

    // 3 cenario de teste

    @Test
    fun `deve retornar 400, ao enviar os descriçaõ do produto vazio`() {

        // cenario

        val category = Category(
            name = "Eletronicos"
        )
        categoryRepository.save(category)

        val idCategoryValido: Long? = category.id

        val uri = URI("/api/products")

        val request = InsertProductRequest(
            name = "Ventilador",
            description = "",
            price = 250.0,
            imgUrl = "imagem.jpg",
            idCategory = idCategoryValido!!
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas
    }

    // 4 cenario de teste

    @Test
    fun `deve retornar 400, ao enviar imagem do produto vazio`() {

        // cenario

        val category = Category(
            name = "Eletronicos"
        )
        categoryRepository.save(category)

        val idCategoryValido: Long? = category.id

        val uri = URI("/api/products")

        val request = InsertProductRequest(
            name = "Ventilador",
            description = "branco, turbo",
            price = 250.0,
            imgUrl = "",
            idCategory = idCategoryValido!!
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas
    }

    // 5 cenario de teste

    @Test
    fun `deve retornar 400, ao enviar preço do produto zerado`() {

        // cenario

        val category = Category(
            name = "Eletronicos"
        )
        categoryRepository.save(category)

        val idCategoryValido: Long? = category.id

        val uri = URI("/api/products")

        val request = InsertProductRequest(
            name = "Ventilador",
            description = "branco, turbo",
            price = 0.0,
            imgUrl = "imagem.jpg",
            idCategory = idCategoryValido!!
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas
    }

    // 6 cenario de teste

    @Test
    fun `deve retornar 404, ao enviar id categoria inexistente`() {

        // cenario

        val idCategoryInexistente: Long? = 5000000

        val uri = URI("/api/products")

        val request = InsertProductRequest(
            name = "Ventilador",
            description = "branco, turbo",
            price = 250.0,
            imgUrl = "imagem.jpg",
            idCategory = idCategoryInexistente!!
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(404))

        // assertivas
    }

    // metodo para desserializar objeto de request
    fun toJson(request: InsertProductRequest): String {
        return objectMapper.writeValueAsString(request)
    }
}