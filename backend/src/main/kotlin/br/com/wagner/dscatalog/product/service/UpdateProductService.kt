package br.com.wagner.dscatalog.product.service

import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.handler.ExceptionGenericValidated
import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import br.com.wagner.dscatalog.product.repository.ProductRepository
import br.com.wagner.dscatalog.product.request.UpdateProductRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateProductService(
    @field:Autowired val productRepository: ProductRepository,
    @field:Autowired val categoryRepository: CategoryRepository
    ) {

    val logger = LoggerFactory.getLogger(UpdateProductService::class.java)

    // metodo contendo a logica para atualizar um produto

    @Transactional
    fun update(id: Long, request: UpdateProductRequest) {
        logger.info("---Execultando a atualização de um produto id $id------")

        // validação do produto, se voltar vazio lança exceção

        val product = productRepository.findById(id).orElseThrow {
            logger.error("----Entrou no if, id do produto não encontrado $id------")
            ResourceNotFoundException("id do produto não encontrado") }

        // validação categoria existente, se voltar vazia lança exception

        val category = categoryRepository.findById(request.idCategory).orElseThrow {
            logger.error("---Entrou no if, id da categoria não encontrada ${request.idCategory}----")
            ResourceNotFoundException("id da categoria não encontrada") }

        // validação nome do produto, unico

        if(productRepository.existsByName(request.name)) {
            logger.error("---Entrou no if, já existe nome do produto cadastrado ${request.name}-----")
            throw ExceptionGenericValidated("nome do produto já existente")
        }

        product.name = request.name
        product.description = request.description
        product.price = request.price
        product.imgUrl = request.imgUrl
        product.category = category
        product.update()
        productRepository.save(product)

        logger.info("---Atualização do produto id $id concluida com sucesso----")

    }

}