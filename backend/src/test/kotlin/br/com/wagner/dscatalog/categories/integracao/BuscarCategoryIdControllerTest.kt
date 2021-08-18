package br.com.wagner.dscatalog.categories.integracao

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.response.BuscarCategoryIdResponse
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
class BuscarCategoryIdControllerTest {

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
    fun `deve retornar 200, com objeto de resposta no corpo`() {

        // cenario

        val category = Category(
            name = "Books"
        )
        categoryRepository.save(category)

        val idCategoryValido = category.id

        val uri = UriComponentsBuilder.fromUriString("/api/categories/{id}").buildAndExpand(idCategoryValido).toUri()

        val response = BuscarCategoryIdResponse(category)
        // ação



        mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().json(toJson(response)))


        // assertivas
    }

    // 2 cenario de teste

    @Test
    fun `deve retornar 404, quando id de categoria buscado não existe`() {

        // cenario

        val category = Category(
            name = "Books"
        )
        categoryRepository.save(category)

        val idcategoryInvalido = 80000L

        val uri = UriComponentsBuilder.fromUriString("/api/categories/{id}").buildAndExpand(idcategoryInvalido).toUri()

        // ação

        mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(404))


        // assertivas
    }


    // metodo para desserializar objeto de resposta

    private fun toJson(response: BuscarCategoryIdResponse): String {
        return objectMapper.writeValueAsString(response)
    }
}