package br.com.wagner.dscatalog.product.service

import br.com.wagner.dscatalog.product.repository.ProductRepository
import br.com.wagner.dscatalog.product.response.ListaProductResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ListarProductService(@field:Autowired val productRepository: ProductRepository) {

    val logger = LoggerFactory.getLogger(ListarProductService::class.java)

    // metodo contendo a logica para listar produtos

    @Transactional
    fun findAllPaged(pageRequest: PageRequest): Page<ListaProductResponse> {

        logger.info("---Execultando a busca pela listagem de produtos------")

        val list = productRepository.findAll(pageRequest)

        val response = list.map { product -> ListaProductResponse(product) }
        logger.info("---Resultado da busca paginada realizada com sucesso----")
        return response

    }
}