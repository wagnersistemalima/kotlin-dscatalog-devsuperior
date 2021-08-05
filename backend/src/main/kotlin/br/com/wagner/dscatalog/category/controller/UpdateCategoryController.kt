package br.com.wagner.dscatalog.category.controller

import br.com.wagner.dscatalog.category.request.UpdateCategoryRequest
import br.com.wagner.dscatalog.category.service.UpdateCategoryService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/api/categories")
class UpdateCategoryController(@field:Autowired val updateCategoryService: UpdateCategoryService) {

    val logger = LoggerFactory.getLogger(UpdateCategoryController::class.java)

    // end point para atualizar uma categoria

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @Valid @RequestBody request: UpdateCategoryRequest): ResponseEntity<Unit> {
        logger.info("---Iniciando a atualização da categoria id $id------")

        updateCategoryService.update(id, request)

        return ResponseEntity.ok().build()
    }
}