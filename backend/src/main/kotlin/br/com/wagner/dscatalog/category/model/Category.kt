package br.com.wagner.dscatalog.category.model

import javax.persistence.*

@Entity
@Table(name = "tb_category")
class Category(

    val name: String
){

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    // equals & hashCode

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Category

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}
