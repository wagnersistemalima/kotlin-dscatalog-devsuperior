package br.com.wagner.dscatalog.product.response

import br.com.wagner.dscatalog.product.model.Product

data class BuscaProductIdResponse(

    val id: Long?,
    val description: String,
    val price: Double,
    val imgUrl: String,
    val categoryId: Long?,
    val categoryName: String

){
    constructor(product: Product): this(product.id, product.description, product.price, product.imgUrl, product.category.id, product.category.name)
}
