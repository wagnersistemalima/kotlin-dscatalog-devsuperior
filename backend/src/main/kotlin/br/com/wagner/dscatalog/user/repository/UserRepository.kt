package br.com.wagner.dscatalog.user.repository

import br.com.wagner.dscatalog.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Long> {


    fun findByEmail(email: String): Optional<User>


}