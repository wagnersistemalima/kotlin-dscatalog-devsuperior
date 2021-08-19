package br.com.wagner.dscatalog.user.integracao

import br.com.wagner.dscatalog.user.request.InsertUserRequest
import br.com.wagner.dscatalog.util.TokenUtil
import com.fasterxml.jackson.databind.ObjectMapper
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
class InsertUserControllerTest {

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    @field:Autowired
    lateinit var tokenUtil: TokenUtil

    // 1 cenario de teste

    @Test
    fun `deve retornar 200, cadastrar um usuario`() {

        val clientUsername = "maria@gmail.com";
        val clientPassword = "123456";

        val accesToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword)

        // cenario
        val uri = URI("/api/users")

        val request = InsertUserRequest(
            fistName = "Joao",
            lastName = "Silva",
            email = "joao@gmail.com",
            password = "123456",
            idRole = 1
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri).header("Authorization", "Bearer $accesToken")
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(201))

        // assertivas
    }

    // 2 cenario de teste

    @Test
    fun `deve retornar 400, quando enviar fistName vazio`() {

        val clientUsername = "maria@gmail.com";
        val clientPassword = "123456";

        val accesToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword)

        // cenario
        val uri = URI("/api/users")

        val request = InsertUserRequest(
            fistName = "",
            lastName = "Silva",
            email = "joao@gmail.com",
            password = "123456",
            idRole = 1
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri).header("Authorization", "Bearer $accesToken")
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas
    }

    // 3 cenario de teste

    @Test
    fun `deve retornar 400, quando enviar lastName vazio`() {

        val clientUsername = "maria@gmail.com";
        val clientPassword = "123456";

        val accesToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword)

        // cenario
        val uri = URI("/api/users")

        val request = InsertUserRequest(
            fistName = "Joao",
            lastName = "",
            email = "joao@gmail.com",
            password = "123456",
            idRole = 1
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri).header("Authorization", "Bearer $accesToken")
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas
    }

    // 4 cenario de teste

    @Test
    fun `deve retornar 400, quando enviar password vazio`() {

        val clientUsername = "maria@gmail.com";
        val clientPassword = "123456";

        val accesToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword)

        // cenario
        val uri = URI("/api/users")

        val request = InsertUserRequest(
            fistName = "Joao",
            lastName = "Silva",
            email = "joao@gmail.com",
            password = "",
            idRole = 1
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri).header("Authorization", "Bearer $accesToken")
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas
    }

    // 5 cenario de teste

    @Test
    fun `deve retornar 404, quando nao encontrar a role`() {

        val clientUsername = "maria@gmail.com";
        val clientPassword = "123456";

        val accesToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword)

        // cenario
        val uri = URI("/api/users")

        val idRoleNaoExiste = 5000L

        val request = InsertUserRequest(
            fistName = "Joao",
            lastName = "Silva",
            email = "joao@gmail.com",
            password = "123456",
            idRole = idRoleNaoExiste
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri).header("Authorization", "Bearer $accesToken")
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(404))

        // assertivas
    }

    // 5 cenario de teste

    @Test
    fun `deve retornar 422, quando tentar cadastrar usuario com email ja existente`() {

        val clientUsername = "maria@gmail.com";
        val clientPassword = "123456";

        val accesToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword)

        // cenario
        val uri = URI("/api/users")

        val request = InsertUserRequest(
            fistName = "Joao",
            lastName = "Silva",
            email = "alex@gmail.com",
            password = "123456",
            idRole = 1
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri).header("Authorization", "Bearer $accesToken")
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(422))

        // assertivas
    }

    // metodo para desserializar objeto da request
    private fun toJson(request: InsertUserRequest): String {
        return  objectMapper.writeValueAsString(request)
    }
}