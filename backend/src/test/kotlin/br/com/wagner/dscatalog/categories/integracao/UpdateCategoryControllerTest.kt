package br.com.wagner.dscatalog.categories.integracao

import br.com.wagner.dscatalog.util.TokenUtil
import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.request.UpdateCategoryRequest
import br.com.wagner.dscatalog.product.model.Product
import br.com.wagner.dscatalog.product.repository.ProductRepository
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
class UpdateCategoryControllerTest {

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

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

    // 1 cenario de teste

    @Test
    fun `deve retornar 200, atualizar uma categoria quando chamado o end point put, passando id categoria valido`() {

        val clientUsername = "maria@gmail.com";
        val clientPassword = "123456";

        val accesToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword)

        // cenario

        val category = Category(
            name = "Eletronics"
        )
        categoryRepository.save(category)
        val idCategoryValido = category.id

        val uri = UriComponentsBuilder.fromUriString("/api/categories/{id}").buildAndExpand(idCategoryValido).toUri()

        val request = UpdateCategoryRequest(
            name = "Limpeza"
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.put(uri).header("Authorization", "Bearer $accesToken")
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(200))


        // assertivas
    }

    // 2 cenario de teste

    @Test
    fun `deve retornar 404, quando tentar atualizar uma categoria que não existe`() {

        val clientUsername = "maria@gmail.com";
        val clientPassword = "123456";

        val accesToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword)

        // cenario

        val category = Category(
            name = "Eletronics"
        )
        categoryRepository.save(category)

        val idCategoriaInexistente = 50000L

        val uri = UriComponentsBuilder.fromUriString("/api/categories/{id}").buildAndExpand(idCategoriaInexistente).toUri()

        val request = UpdateCategoryRequest(
            name = "Limpeza"
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.put(uri).header("Authorization", "Bearer $accesToken")
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(404))


        // assertivas
    }

    // 3 cenario de teste

    @Test
    fun `deve retornar 422, quando tentar atualizar uma categoria com produtos associado a ela`() {

        val clientUsername = "maria@gmail.com";
        val clientPassword = "123456";

        val accesToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword)

        // cenario

        val category = Category(
            name = "eletronics"
        )
        categoryRepository.save(category)

        val product1 = Product(
            name = "Ventilador",
            description = "branco, turbo",
            price = 250.0,
            imgUrl = "http://imagem.jpg",
            category = category
        )
        productRepository.save(product1)

        val idCategoriaExistente = category.id

        val uri = UriComponentsBuilder.fromUriString("/api/categories/{id}").buildAndExpand(idCategoriaExistente).toUri()

        val request = UpdateCategoryRequest(
            name = "limpeza"
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.put(uri).header("Authorization", "Bearer $accesToken")
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(422))

        // assertivas
    }


    // metodo para desserializar objeto de request

    private fun toJson(request: UpdateCategoryRequest): String {
        return objectMapper.writeValueAsString(request)
    }
}