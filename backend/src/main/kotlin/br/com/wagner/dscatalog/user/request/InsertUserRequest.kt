package br.com.wagner.dscatalog.user.request

import br.com.wagner.dscatalog.role.model.Role
import br.com.wagner.dscatalog.user.model.User
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class InsertUserRequest(

    @field:NotBlank
    val fistName: String,

    @field:NotBlank
    val lastName: String,

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String,

    val IdRole: Long,
) {

    // metodo contendo a logica para converter objeto em entidade

    fun toModel(role: Role): User {
        val user = User(
            fistName = fistName,
            lastName = lastName,
            email = email.toLowerCase(),
            password = password
        )
        user.roles.add(role)
        return user
    }
}
