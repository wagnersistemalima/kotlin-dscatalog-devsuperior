package br.com.wagner.dscatalog.product.service

import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.handler.ExceptionGenericValidated
import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import br.com.wagner.dscatalog.product.repository.ProductRepository
import br.com.wagner.dscatalog.product.request.InsertProductRequest
import br.com.wagner.dscatalog.product.response.InsertProductResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InsertProductService(
    @field:Autowired val productRepository: ProductRepository,
    @field:Autowired val categoryRepository: CategoryRepository

    ) {

    val logger = LoggerFactory.getLogger(InsertProductService::class.java)

    // metodo contendo a logica para cadastrar um produto

    @Transactional
    fun insert(request: InsertProductRequest): InsertProductResponse {
        logger.info("---Execultando o cadastro de um novo produto-------")

        // validação de categoria existente

        val category = categoryRepository.findById(request.idCategory).orElseThrow {
            logger.error("----Entrou no if, categoria não encontrada-----")
            ResourceNotFoundException("categoria não encontrada") }

        // validação produto nao pode ter mesmo nome

        val obj = productRepository.findByName(request.name.toLowerCase())

        if(obj.isPresent) {
            logger.error("----Entrou no if, produto com este nome ja cadastrado-----")
            throw ExceptionGenericValidated("Campo unico, produto com este nome ja cadastrado")
        }

        val product = request.toModel(category)
        productRepository.save(product)

        val response = InsertProductResponse(product)
        logger.info("-----Produto salvo com sucesso------")
        return response

    }

}