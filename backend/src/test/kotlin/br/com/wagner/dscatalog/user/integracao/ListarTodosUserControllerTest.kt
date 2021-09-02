package br.com.wagner.dscatalog.user.integracao

import br.com.wagner.dscatalog.user.repository.UserRepository
import br.com.wagner.dscatalog.user.response.ListaUserResponse
import br.com.wagner.dscatalog.util.TokenUtil
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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
class ListarTodosUserControllerTest {

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    @field:Autowired
    lateinit var tokenUtil: TokenUtil

    @field:Autowired
    lateinit var userRepository: UserRepository

    // 1 cenario de teste

    @Test
    fun `deve retornar 200, com lista de usuarios`() {

        // cenario

        val page: Int = 0
        val linesPerPage: Int = 12
        val direction: String = "ASC"
        val orderBy: String = "fistName"

        val pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy)

        val uri = URI("/api/users")

        // login
        val clientUsername = "maria@gmail.com";
        val clientPassword = "123456";

        val accesToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword)

        //ação

        val list = userRepository.findAll(pageRequest)

        val response = list.map { user -> ListaUserResponse(user) }

        mockMvc.perform(MockMvcRequestBuilders.get(uri).header("Authorization", "Bearer $accesToken")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().json(toJson(response)))

        // assertivas

        Assertions.assertFalse(response.isEmpty)
        Assertions.assertEquals(12, response.size)
    }

    // metodo para desserializar objeto de resposta

    fun toJson(response: Page<ListaUserResponse>): String {
        return objectMapper.writeValueAsString(response)
    }

}