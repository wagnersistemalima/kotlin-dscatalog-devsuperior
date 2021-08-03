package br.com.wagner.dscatalog.category.controller

import br.com.wagner.dscatalog.category.response.ListaCategoryResponse
import br.com.wagner.dscatalog.category.service.ListarCategoryService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ListarTodasCategoryController(@field:Autowired val listarCategoryService: ListarCategoryService) {

    val logger = LoggerFactory.getLogger(ListarTodasCategoryController::class.java)

    // end point para listar categorias

    @GetMapping("/categories")
    fun findAll(): ResponseEntity<MutableList<ListaCategoryResponse>> {
        logger.info("----Iniciando a busca das categorias existentes-------")

        val response = listarCategoryService.findAll()

        return ResponseEntity.ok().body(response)
    }
}