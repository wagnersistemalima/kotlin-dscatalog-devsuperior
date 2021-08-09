package br.com.wagner.dscatalog.category.model

import br.com.wagner.dscatalog.product.model.Product
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tb_category")
class Category(

    var name: String
){

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    val dataRegistro = LocalDateTime.now()

    var updateDataRegistro: LocalDateTime? = null

    // cascadeType.All  -> propagara todas as açoes para os dependentes
    @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "category", orphanRemoval = true)  // uma categoria pode possui varios produtos
    val products: MutableList<Product> = mutableListOf()

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

    // metodo auxiliar para toda vez que atualizar uma categoria no banco, salvar o instante da atualização

    fun update() {
        updateDataRegistro = LocalDateTime.now()
    }

    fun updateName(name: String) {
        this.name = name.toLowerCase()
    }

}
