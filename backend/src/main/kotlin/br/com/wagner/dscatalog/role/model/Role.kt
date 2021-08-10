package br.com.wagner.dscatalog.role.model

import javax.persistence.*

@Entity
@Table(name = "tb_role")
class Role(
    val authority: String
){

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
