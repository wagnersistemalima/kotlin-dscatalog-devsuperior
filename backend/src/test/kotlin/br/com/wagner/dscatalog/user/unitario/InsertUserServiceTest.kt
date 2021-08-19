package br.com.wagner.dscatalog.user.unitario

import br.com.wagner.dscatalog.handler.ExceptionGenericValidated
import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import br.com.wagner.dscatalog.role.model.Role
import br.com.wagner.dscatalog.role.repository.RoleRepository
import br.com.wagner.dscatalog.user.repository.UserRepository
import br.com.wagner.dscatalog.user.request.InsertUserRequest
import br.com.wagner.dscatalog.user.service.InsertUserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class InsertUserServiceTest {

    @field:InjectMocks
    lateinit var insertUserService: InsertUserService

    @field:Mock
    lateinit var roleRepository: RoleRepository

    @field:Mock
    lateinit var userRepository: UserRepository

    // 1 cenario de teste

    @Test
    fun `deve inserir um usuario e nao lançar exceptions`() {

        // cenario

        val role = Role(
            authority = "admin"
        )

        val idRoleExistente = 1L

        val request = InsertUserRequest(
            fistName = "Joao",
            lastName = "Silva",
            email = "alex@gmail.com",
            password = "123456",
            idRole = 1
        )

        //ação

        val user = request.toModel(role)

        //comportamento
        Mockito.`when`(roleRepository.findById(idRoleExistente)).thenReturn(Optional.of(role))

        Mockito.`when`(userRepository.findByEmail(request.email)).thenReturn(Optional.empty())

        Mockito.`when`(userRepository.save(user)).thenReturn(user)

        // assertivas

        Assertions.assertDoesNotThrow { insertUserService.insert(request) }

        Mockito.verify(userRepository, Mockito.times(1)).save(user)
    }

    // 2 cenario de teste

    @Test
    fun `deve lançar exceptions ResourceNotFoundException, quando id role não existente`() {

        // cenario

        val role = Role(
            authority = "admin"
        )

        val idRoleNaoExiste = 5000L

        val request = InsertUserRequest(
            fistName = "Joao",
            lastName = "Silva",
            email = "alex@gmail.com",
            password = "123456",
            idRole = idRoleNaoExiste
        )

        //ação

        val user = request.toModel(role)

        //comportamento
        Mockito.`when`(roleRepository.findById(idRoleNaoExiste)).thenReturn(Optional.empty())

        Mockito.`when`(userRepository.findByEmail(request.email)).thenReturn(Optional.empty())

        // assertivas

        Assertions.assertThrows(ResourceNotFoundException::class.java) {insertUserService.insert(request)}

        Mockito.verify(userRepository, Mockito.times(0)).save(user)
    }

    // 3 cenario de teste

    @Test
    fun `deve lançar exceptions ExceptionGenericValidation, quando email ja cadastrado em outro usuario`() {

        // cenario

        val role = Role(
            authority = "admin"
        )

        val idRoleExistente = 1L

        val request = InsertUserRequest(
            fistName = "Joao",
            lastName = "Silva",
            email = "alex@gmail.com",
            password = "123456",
            idRole = idRoleExistente
        )

        //ação

        val user = request.toModel(role)

        //comportamento
        Mockito.`when`(roleRepository.findById(idRoleExistente)).thenReturn(Optional.of(role))

        Mockito.`when`(userRepository.findByEmail(request.email)).thenReturn(Optional.of(user))

        // assertivas

        Assertions.assertThrows(ExceptionGenericValidated::class.java) {insertUserService.insert(request)}

        Mockito.verify(userRepository, Mockito.times(0)).save(user)
    }
}