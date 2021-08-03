package br.com.wagner.dscatalog.category.controller

import br.com.wagner.dscatalog.category.request.InsertCategoryRequest
import br.com.wagner.dscatalog.category.service.InsertCategoryService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/api")
class InsertCategoryController(@field:Autowired val insertCategoryService: InsertCategoryService) {

    val logger = LoggerFactory.getLogger(InsertCategoryController::class.java)

    // end point para inserir categoria

    @PostMapping("/categories")
    fun insert(@Valid @RequestBody request: InsertCategoryRequest): ResponseEntity<Any> {
        logger.info("------Iniciando cadastro de uma nova categoria ${request.name}-------")

        val response = insertCategoryService.insert(request)

        val uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("{id}")
            .buildAndExpand(response.id).toUri()

        return  ResponseEntity.created(uri).build()
    }
}