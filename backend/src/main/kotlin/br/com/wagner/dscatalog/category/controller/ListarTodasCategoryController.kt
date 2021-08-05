package br.com.wagner.dscatalog.category.controller

import br.com.wagner.dscatalog.category.response.ListaCategoryResponse
import br.com.wagner.dscatalog.category.service.ListarCategoryService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ListarTodasCategoryController(@field:Autowired val listarCategoryService: ListarCategoryService) {

    val logger = LoggerFactory.getLogger(ListarTodasCategoryController::class.java)

    // end point para listar categorias

    @GetMapping("/categories")
    fun findAll(
        @RequestParam(value = "page", defaultValue = "0",) page: Int,
        @RequestParam(value = "linesPerPage", defaultValue = "12") linesPerPage: Int,
        @RequestParam(value = "direction", defaultValue = "ASC") direction: String,
        @RequestParam(value = "orderBy", defaultValue = "name") orderBy: String

    ): ResponseEntity<Page<ListaCategoryResponse>> {
        logger.info("----Iniciando a busca das categorias existentes-------")

        val pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy)

        val response = listarCategoryService.findAllPaged(pageRequest)

        return ResponseEntity.ok().body(response)
    }
}