package br.com.wagner.dscatalog.user.service

import br.com.wagner.dscatalog.handler.ExceptionGenericValidated
import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import br.com.wagner.dscatalog.role.repository.RoleRepository
import br.com.wagner.dscatalog.user.repository.UserRepository
import br.com.wagner.dscatalog.user.request.InsertRoleRequest
import br.com.wagner.dscatalog.user.request.InsertUserRequest
import br.com.wagner.dscatalog.user.response.InsertUserResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InsertUserService(
    @field:Autowired val userRepository: UserRepository,
    @field:Autowired val roleRepository: RoleRepository

    ) {

    val logger = LoggerFactory.getLogger(InsertUserService::class.java)

    // metodo para cadastrar usuario no banco

    @Transactional
    fun insert(request: InsertUserRequest): InsertUserResponse {
        logger.info("----Execultando cadastro de um novo usuario ${request.fistName} ------")

        // validação de role-perfil

        val role = roleRepository.findById(request.idRole).orElseThrow {
            logger.error("----Id ${request.idRole} role não encontrado-----")
            ResourceNotFoundException("id role não encontrado") }

        // validações email unico

        val userEmail = userRepository.findByEmail(request.email)

        if(userEmail.isPresent) {
            logger.error("----Entrou no if, campo unico, email já cadastrado para este usuario ${request.fistName} -----")
            throw ExceptionGenericValidated("campo unico, email já cadastrado")
        }

        val user = request.toModel(role)

        userRepository.save(user)
        logger.info("---Usuario cadastrado com sucesso---")
        return InsertUserResponse(user)
    }





}