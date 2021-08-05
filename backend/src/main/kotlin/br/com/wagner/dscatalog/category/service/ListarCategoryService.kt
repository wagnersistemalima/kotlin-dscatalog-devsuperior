package br.com.wagner.dscatalog.category.service

import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.response.ListaCategoryResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ListarCategoryService(@field:Autowired val categoryRepository: CategoryRepository) {

    val logger = LoggerFactory.getLogger(ListarCategoryService::class.java)

    // metodo contendo a logica para listar categorias no banco

    @Transactional(readOnly = true)
    fun findAllPaged(pageRequest: PageRequest): Page<ListaCategoryResponse> {
        logger.info("---Execultando a busca por categorias-----")

        val list = categoryRepository.findAll(pageRequest)

        val response = list.map { category -> ListaCategoryResponse(category) }

        logger.info("---Listagem de categorias realizada com sucesso----")
        return response
    }
}