package br.com.wagner.dscatalog.user.unitario

import br.com.wagner.dscatalog.role.model.Role
import br.com.wagner.dscatalog.user.model.User
import br.com.wagner.dscatalog.user.repository.UserRepository
import br.com.wagner.dscatalog.user.service.ListaUserService
import com.sun.el.stream.Stream
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.data.domain.*
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class ListarUserServiceTest {

    @field:InjectMocks
    lateinit var listarUserService: ListaUserService

    @field:Mock
    lateinit var userRepository: UserRepository

    // 1 cenario de teste

    @Test
    fun `deve retornar uma lista userDTO paginada`() {

        // cenario

        val role = Role(
            authority = "Admin"
        )

        val user1 = User(
            fistName = "Joao",
            lastName = "Silva",
            email = "joao@gmail.com",
            password = "123456"
        )
        user1.roles.add(role)

        val user2 = User(
            fistName = "Pedro",
            lastName = "Silva",
            email = "pedro@gmail.com",
            password = "123456"
        )
        user2.roles.add(role)

        val page: Int = 0
        val linesPerPage: Int = 12
        val direction: String = "ASC"
        val orderBy: String = "fistName"

        val pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy)

        val list = PageImpl<User>(listOf(user1, user2))  // instanciando Page

        Mockito.`when`(userRepository.findAll(pageRequest)).thenReturn(list)

        // ação

        Assertions.assertDoesNotThrow { listarUserService.findAll(pageRequest) }

        //assertivas
    }
}