package br.com.wagner.dscatalog.user.model

import br.com.wagner.dscatalog.role.model.Role
import javax.persistence.*

@Entity
@Table(name = "tb_user")
class User(

    val fistName: String,
    val lastName: String,
    val email: String,
    val password: String
){
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    // associação , um usuario pode ter um ou mais perfis

    @field:ManyToMany(fetch = FetchType.EAGER)     // dados do perfil carregar automaticamente
    val roles: MutableList<Role> = mutableListOf()

    // equals & hasCode

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}
