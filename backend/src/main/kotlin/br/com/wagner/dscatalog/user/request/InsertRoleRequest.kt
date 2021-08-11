package br.com.wagner.dscatalog.user.request

import br.com.wagner.dscatalog.role.model.Role

data class InsertRoleRequest(
    val id: Long?,
    val authority: String
){
    constructor(role: Role): this(role.id, role.authority)
}
