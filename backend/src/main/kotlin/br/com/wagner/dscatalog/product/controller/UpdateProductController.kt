package br.com.wagner.dscatalog.product.controller

import br.com.wagner.dscatalog.product.request.UpdateProductRequest
import br.com.wagner.dscatalog.product.service.UpdateProductService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/api/products")
class UpdateProductController(@field:Autowired val updateProductService: UpdateProductService) {

    val logger = LoggerFactory.getLogger(UpdateProductController::class.java)

    // end point para atualizar um produto

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long,@Valid @RequestBody request: UpdateProductRequest): ResponseEntity<Unit> {
        logger.info("---Iniciando a atualização de um produto $id ---------")

        updateProductService.update(id, request)

        return ResponseEntity.ok().build()
    }
}