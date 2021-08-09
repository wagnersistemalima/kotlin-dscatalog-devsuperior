package br.com.wagner.dscatalog.product.model

import br.com.wagner.dscatalog.category.model.Category
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tb_product")
class Product(

    val name: String,

    @field:Column(columnDefinition = "TEXT")
    val description: String,
    val price: Double,
    val imgUrl: String,

    @field:ManyToOne          // associação -> varios produtos pode pertence a uma categoria
    @field:JoinColumn(name = "category_id")   //  chave estrangeira na tabela tb_product
    val category: Category

) {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @field:Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    val date: LocalDateTime = LocalDateTime.now()

    @field:Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    var dateUpdate: LocalDateTime? = null

    // equals & hashCode

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    // metodo auxiliar para toda vez que for atualizar um produto, salvar o instante atual

    fun update() {
        dateUpdate = LocalDateTime.now()
    }


}
