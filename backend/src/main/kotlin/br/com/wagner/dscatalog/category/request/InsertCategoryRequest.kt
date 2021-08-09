package br.com.wagner.dscatalog.category.request

import br.com.wagner.dscatalog.category.model.Category
import javax.validation.constraints.NotBlank

data class InsertCategoryRequest(

    @field:NotBlank
    val name: String
) {

    // metodo para converter request em entidade

    fun toModel(): Category {
        return Category(name.toLowerCase())
    }
}



