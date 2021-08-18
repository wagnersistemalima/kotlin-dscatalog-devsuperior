package br.com.wagner.dscatalog.user.service

import br.com.wagner.dscatalog.user.repository.UserRepository
import br.com.wagner.dscatalog.user.response.ListaUserResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ListaUserService(@field:Autowired val userRepository: UserRepository) {

    val logger = LoggerFactory.getLogger(ListaUserService::class.java)

    // metodo contendo a logica para listar usuarios

    @Transactional
    fun findAll(pageRequest: PageRequest): Page<ListaUserResponse> {
        logger.info("----Execultando a listagem de usuarios-----")

        val list = userRepository.findAll(pageRequest)

        val response = list.map { user -> ListaUserResponse(user) }

        logger.info("---Listagem concluida com sucesso----")
        return response

    }




}