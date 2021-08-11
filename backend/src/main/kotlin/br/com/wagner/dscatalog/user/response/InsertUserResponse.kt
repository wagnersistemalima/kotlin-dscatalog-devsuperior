package br.com.wagner.dscatalog.user.response

import br.com.wagner.dscatalog.user.model.User

data class InsertUserResponse(

    val id: Long?
){
    constructor(user: User): this (user.id)
}
