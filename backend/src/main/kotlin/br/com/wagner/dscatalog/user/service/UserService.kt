package br.com.wagner.dscatalog.user.service

import br.com.wagner.dscatalog.user.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
    @field:Autowired val userRepository: UserRepository
): UserDetailsService {

    val logger = LoggerFactory.getLogger(UserDetailsService::class.java)

    override fun loadUserByUsername(username: String?): UserDetails {
        logger.info("---Execultando a autenticação do usuario $username ------")

        val user = userRepository.findByEmail(username!!).orElseThrow {
            logger.error("----Entrou no if, email não encontrado para autenticação $username ------")
            UsernameNotFoundException("Email not found") }

        logger.info("---- Usuario encontrado $username ----")
        return user

    }
}