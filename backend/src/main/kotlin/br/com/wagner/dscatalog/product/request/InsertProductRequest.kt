package br.com.wagner.dscatalog.product.request

import br.com.wagner.dscatalog.category.model.Category
import br.com.wagner.dscatalog.category.request.InsertCategoryRequest
import br.com.wagner.dscatalog.product.model.Product
import br.com.wagner.dscatalog.product.repository.ProductRepository
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class InsertProductRequest(

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val description: String,

    @field:Positive
    val price: Double,

    @field:NotBlank
    val imgUrl: String,

    val idCategory: Long

) {

    // metodo para converter request em entidade

    fun toModel(category: Category): Product {
        return Product(name, description, price, imgUrl, category)
    }
}


