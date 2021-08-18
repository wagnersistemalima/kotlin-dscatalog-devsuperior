package br.com.wagner.dscatalog.user.response

import br.com.wagner.dscatalog.role.model.Role
import br.com.wagner.dscatalog.user.model.User

data class ListaUserResponse(

    val id: Long?,
    val fistName: String,
    val lastName: String,
    val email: String,
    val roles: MutableList<Role>
){
    constructor(user: User): this (user.id, user.fistName, user.lastName, user.email, user.roles)
}
