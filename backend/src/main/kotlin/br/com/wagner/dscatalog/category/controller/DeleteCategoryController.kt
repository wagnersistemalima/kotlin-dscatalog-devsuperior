package br.com.wagner.dscatalog.category.controller

import br.com.wagner.dscatalog.category.service.DeleteCategoryService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categories")
class DeleteCategoryController(@field:Autowired val deleteCategoryService: DeleteCategoryService) {

    val logger = LoggerFactory.getLogger(DeleteCategoryController::class.java)

    // end point para deletar category

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Unit> {

        logger.info("---Iniciando a deleção de uma categoria id $id----------")

        deleteCategoryService.delete(id)

        return  ResponseEntity.noContent().build()
    }
}