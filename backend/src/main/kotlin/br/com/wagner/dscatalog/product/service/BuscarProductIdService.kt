package br.com.wagner.dscatalog.product.service

import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import br.com.wagner.dscatalog.product.repository.ProductRepository
import br.com.wagner.dscatalog.product.response.BuscaProductIdResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BuscarProductIdService(@field:Autowired val productRepository: ProductRepository) {

    val logger = LoggerFactory.getLogger(BuscarProductIdService::class.java)

    // metodo contendo a logica para buscar produto por id

    @Transactional
    fun findById(id: Long): BuscaProductIdResponse {
        logger.info("---Execultando a busca por produto id $id ----")

        val product = productRepository.findById(id).orElseThrow {
            logger.error("---Entrou no if, id $id nao encontrado -----")
            ResourceNotFoundException("Id do produto não encontrado") }

        val response = BuscaProductIdResponse(product)

        logger.info("--Busca realizada com sucesso---")
        return response

    }

}