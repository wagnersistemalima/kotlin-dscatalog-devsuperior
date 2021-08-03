package br.com.wagner.dscatalog.categories.integracao

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.request.InsertCategoryRequest
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
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class InsertCategoryControllerTest {

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    @field:Autowired
    lateinit var categoryRepository: CategoryRepository

    // rodar antes de cada teste
    @BeforeEach
    internal fun setUp() {
        categoryRepository.deleteAll()
    }

    // rodar depois de cada teste
    @AfterEach
    internal fun tearDown() {
        categoryRepository.deleteAll()
    }

    // 1 cenario de teste

    @Test
    fun `deve retornar 200, ao receber requisição com dados validados`() {

        // cenario

        val uri = URI("/api/categories")

        val request = InsertCategoryRequest(
            name = "Filmes"
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(201))

        // assertivas
    }

    // 2 cenario de teste

    @Test
    fun `deve retornar 400, ao receber requisição com nome vazio`() {

        // cenario

        val uri = URI("/api/categories")

        val request = InsertCategoryRequest(
            name = ""
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas
    }

    // 3 cenario de teste

    @Test
    fun `deve retornar 422, ao receber requisição com nome da categoria ja cadastrada`() {

        // cenario

        val uri = URI("/api/categories")

        val request = InsertCategoryRequest(
            name = "Books"
        )

        val category = Category(
            name = "Books"
        )
        categoryRepository.save(category)

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(422))

        // assertivas
    }

    // metodo para desserializar objeto request

    fun toJson(request: InsertCategoryRequest): String {
        return objectMapper.writeValueAsString(request)
    }
}