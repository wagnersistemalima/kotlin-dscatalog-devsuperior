package br.com.wagner.dscatalog.categories.integracao

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.repository.CategoryRepository
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
class DeleteCategoryControllerTest {

    @field:Autowired
    lateinit var mockMvc: MockMvc


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
    fun `deve retornar 204, quando id category valido, deletar uma category`() {

        // cenario

        val category = Category(
            name = "Livros"
        )
        categoryRepository.save(category)

        val idCategoryValido = category.id

        val uri = UriComponentsBuilder.fromUriString("/api/categories/{id}").buildAndExpand(idCategoryValido).toUri()

        // ação

        mockMvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(204))

        // assertivas

    }

    // 2 cenario de teste

    @Test
    fun `deve retornar 404, quando id category nao existir`() {

        // cenario

        val category = Category(
            name = "Livros"
        )
        categoryRepository.save(category)

        val idCategoryInexistente = 5000L

        val uri = UriComponentsBuilder.fromUriString("/api/categories/{id}").buildAndExpand(idCategoryInexistente).toUri()

        // ação

        mockMvc.perform(MockMvcRequestBuilders.delete(uri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(404))

        // assertivas

    }
}