package br.com.wagner.dscatalog.category.controller

import br.com.wagner.dscatalog.category.response.BuscarCategoryIdResponse
import br.com.wagner.dscatalog.category.service.BuscarCategoryIdService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/categories")
class BuscarCategoryIdController(@field:Autowired val buscarCategoryIdService: BuscarCategoryIdService) {

    val logger = LoggerFactory.getLogger(BuscarCategoryIdController::class.java)

    // end point para buscar categoria pr id

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long) : ResponseEntity<BuscarCategoryIdResponse> {
        logger.info("-----Iniciando a busca de uma categoria por id $id-----")

        val response = buscarCategoryIdService.findById(id)

        return ResponseEntity.ok().body(response)
    }
}