package br.com.wagner.dscatalog.product.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class UpdateProductRequest(

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val description: String,

    @field:Positive
    val price: Double,

    @field:NotBlank
    val imgUrl: String,

    val idCategory: Long
)
