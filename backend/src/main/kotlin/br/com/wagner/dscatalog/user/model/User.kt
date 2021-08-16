package br.com.wagner.dscatalog.user.model

import br.com.wagner.dscatalog.role.model.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors
import javax.persistence.*

@Entity
@Table(name = "tb_user")
class User(

    val fistName: String,
    val lastName: String,
    val email: String,
    private val password: String

) : UserDetails {
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

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.stream().map { role -> SimpleGrantedAuthority(role.authority) }.collect(Collectors.toList())
    }


    override fun getPassword(): String {
        return password
    }


    override fun getUsername(): String {
        return email
    }

    // a conta não está expirada?
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    // a conta não está bloqueada?
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    // as credenciais não está expirada?
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    // o usuario está habilitado?
    override fun isEnabled(): Boolean {
        return true
    }

}
