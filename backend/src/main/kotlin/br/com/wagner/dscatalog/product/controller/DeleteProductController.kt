package br.com.wagner.dscatalog.product.controller

import br.com.wagner.dscatalog.product.service.DeleteProductService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class DeleteProductController(@field:Autowired val deleteProductService: DeleteProductService) {

    val logger = LoggerFactory.getLogger(DeleteProductController::class.java)

    // end point para deletar um produto por id

    @DeleteMapping("/{id}")
    fun delete (@PathVariable("id") id: Long) : ResponseEntity<Any> {
        logger.info("----Iniciando a deleção do produto de id $id---------------")

        deleteProductService.delete(id)

        return ResponseEntity.noContent().build()
    }
}