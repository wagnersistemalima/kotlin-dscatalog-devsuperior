package br.com.wagner.dscatalog.product.response

import br.com.wagner.dscatalog.product.model.Product

data class InsertProductResponse(

    val id: Long?
) {
    constructor(product: Product): this (product.id)
}