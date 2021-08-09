package br.com.wagner.dscatalog.product.controller

import br.com.wagner.dscatalog.product.response.ListaProductResponse
import br.com.wagner.dscatalog.product.service.ListarProductService
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
@RequestMapping("/api/products")
class ListarTodasProductController(@field:Autowired val listarProductService: ListarProductService) {

    val logger = LoggerFactory.getLogger(ListarTodasProductController::class.java)

    @GetMapping
    fun findAll(

        @RequestParam(value = "page", defaultValue = "0",) page: Int,
        @RequestParam(value = "linesPerPage", defaultValue = "12") linesPerPage: Int,
        @RequestParam(value = "direction", defaultValue = "ASC") direction: String,
        @RequestParam(value = "orderBy", defaultValue = "name") orderBy: String
    ): ResponseEntity<Page<ListaProductResponse>> {
        logger.info("----Iniciando a listagem de todos os produtos-----")

        val pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy)

        val response = listarProductService.findAllPaged(pageRequest)

        return  ResponseEntity.ok().body(response)
    }
}