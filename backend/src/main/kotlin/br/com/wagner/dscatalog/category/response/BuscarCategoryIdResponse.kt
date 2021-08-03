package br.com.wagner.dscatalog.category.response

import br.com.wagner.dscatalog.category.model.Category

data class BuscarCategoryIdResponse(

    val id: Long?,
    val name: String
) {
    constructor(category: Category): this(category.id, category.name)
}