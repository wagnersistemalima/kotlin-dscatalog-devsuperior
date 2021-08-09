package br.com.wagner.dscatalog.product.controller

import br.com.wagner.dscatalog.product.response.BuscaProductIdResponse
import br.com.wagner.dscatalog.product.service.BuscarProductIdService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class BuscarProductIdController(@field:Autowired val buscarProductIdService: BuscarProductIdService) {

    val logger = LoggerFactory.getLogger(BuscarProductIdController::class.java)

    // end pint para buscar produto por id

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<BuscaProductIdResponse> {
        logger.info("---Iniciando a busca pelo produto $id -----")

        val response = buscarProductIdService.findById(id)

        return ResponseEntity.ok().body(response)
    }
}