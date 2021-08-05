package br.com.wagner.dscatalog.categories.integracao

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.request.UpdateCategoryRequest
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
    fun `deve retornar 200, atualizar uma categoria quando chamado o end point put, passando id categoria valido`() {

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

        mockMvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(200))


        // assertivas
    }

    // 2 cenario de teste

    @Test
    fun `deve retornar 404, quando tentar atualizar uma categoria que não existe`() {

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

        mockMvc.perform(MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(404))


        // assertivas
    }

    // metodo para desserializar objeto de request

    private fun toJson(request: UpdateCategoryRequest): String {
        return objectMapper.writeValueAsString(request)
    }
}