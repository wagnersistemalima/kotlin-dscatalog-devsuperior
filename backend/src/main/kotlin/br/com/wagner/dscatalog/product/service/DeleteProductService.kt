package br.com.wagner.dscatalog.product.service

import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import br.com.wagner.dscatalog.product.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteProductService(@field:Autowired val productRepository: ProductRepository) {

    val logger = LoggerFactory.getLogger(DeleteProductService::class.java)

    // metodo para deletar um produto por id

    @Transactional
    fun delete(id: Long) {
        logger.info("--Execultando a deleção de um produto por id $id-----")

        // validação

        val product = productRepository.findById(id).orElseThrow {
            logger.error("----Id $id do produto não encontrado para deleção----")
            ResourceNotFoundException("id $id do produto não encontrado") }

        productRepository.delete(product)
        logger.info("----Produto de id $id deletado com sucesso------")
    }

}