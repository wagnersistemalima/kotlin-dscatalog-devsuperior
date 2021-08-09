package br.com.wagner.dscatalog.product.response

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.response.BuscarCategoryIdResponse
import br.com.wagner.dscatalog.product.model.Product

data class ListaProductResponse(

    val id: Long?,
    val name: String,
    val description: String,
    val price: Double,
    val imgUrl: String,
    val categoryId: Long?,
    val categoryName: String

) {

    constructor(product: Product): this(product.id, product.name, product.description, product.price, product.imgUrl, product.category.id, product.category.name)

}