package br.com.wagner.dscatalog.categories.integracao

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.response.ListaCategoryResponse
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
import java.util.stream.Collectors

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@ActiveProfiles("test")
@Transactional
class ListarTodasCategoryControllerTest {

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

    @Test
    fun `deve retornar 200, com lista de categorias`() {

        // cenario

        val uri = URI("/api/categories")

        val category01 = Category(
            name = "Bocks"
        )
        categoryRepository.save(category01)

        val category02 = Category(
            name = "Filmes"
        )
        categoryRepository.save(category02)

        // ação

        val list = categoryRepository.findAll()

        val response = list.stream().map { category -> ListaCategoryResponse(category) }.collect(Collectors.toList())

        mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().json(toJson(response)))

        // assertivas
    }

    // metodo para desserializar objeto de resposta

    private fun toJson(response: MutableList<ListaCategoryResponse>): String {
        return objectMapper.writeValueAsString(response)
    }
}