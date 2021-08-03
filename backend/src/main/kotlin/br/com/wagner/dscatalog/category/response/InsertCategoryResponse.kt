package br.com.wagner.dscatalog.category.response

import br.com.wagner.dscatalog.category.model.Category

data class InsertCategoryResponse(

    val id: Long?
){
    constructor(category: Category): this(category.id)
}