package br.com.wagner.dscatalog.category.service

import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.request.InsertCategoryRequest
import br.com.wagner.dscatalog.category.response.InsertCategoryResponse
import br.com.wagner.dscatalog.handler.ExceptionGenericValidated
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InsertCategoryService(@field:Autowired val categoryRepository: CategoryRepository) {

    val logger = LoggerFactory.getLogger(InsertCategoryService::class.java)

    // metodo contendo a logica para cadastrar uma nova categoria

    fun insert(request: InsertCategoryRequest): InsertCategoryResponse {
        logger.info("---Execultando o cadastro de uma nova categoria  ${request.name}----")

        // fazer validação

        if(categoryRepository.existsByName(request.name)) {
            logger.error("----Caio no if, Campo unico, categoria já existente-----")
            throw ExceptionGenericValidated("Campo unico, categoria já existente")
        }

        val category = request.toModel()

        categoryRepository.save(category)

        val response = InsertCategoryResponse(category)

        logger.info("----category cadastrada com sucesso ${request.name}--------")
        return response

    }


}