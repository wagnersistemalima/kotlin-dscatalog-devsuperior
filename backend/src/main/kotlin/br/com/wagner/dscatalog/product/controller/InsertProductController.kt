package br.com.wagner.dscatalog.product.controller

import br.com.wagner.dscatalog.product.request.InsertProductRequest
import br.com.wagner.dscatalog.product.response.InsertProductResponse
import br.com.wagner.dscatalog.product.service.InsertProductService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/api/products")
class InsertProductController(@field:Autowired val insertProductService: InsertProductService) {

    val logger = LoggerFactory.getLogger(InsertProductController::class.java)

    // end point para inserir um novo produto

    @PostMapping
    fun insert(@Valid @RequestBody request: InsertProductRequest): ResponseEntity<InsertProductResponse> {
        logger.info("----Iniciando cadastro de um novo produto------")

        val response = insertProductService.insert(request)

        val uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("{id}")
            .buildAndExpand(response.id).toUri()

        return  ResponseEntity.created(uri).build()
    }
}