package br.com.wagner.dscatalog.product.response

import br.com.wagner.dscatalog.product.model.Product
import java.time.LocalDateTime

data class BuscaProductIdResponse(

    val id: Long?,
    val name: String,
    val description: String,
    val price: Double,
    val imgUrl: String,
    val categoryId: Long?,
    val categoryName: String,
    val date: LocalDateTime,
    val updateDate: LocalDateTime?

){
    constructor(product: Product): this(product.id, product.name,  product.description, product.price, product.imgUrl, product.category.id, product.category.name, product.date, product.dateUpdate)
}
