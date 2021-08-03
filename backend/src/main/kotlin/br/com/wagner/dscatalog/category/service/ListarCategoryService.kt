package br.com.wagner.dscatalog.category.service

import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.response.ListaCategoryResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class ListarCategoryService(@field:Autowired val categoryRepository: CategoryRepository) {

    val logger = LoggerFactory.getLogger(ListarCategoryService::class.java)

    // metodo contendo a logica para listar categorias no banco

    @Transactional(readOnly = true)
    fun findAll(): MutableList<ListaCategoryResponse> {
        logger.info("---Execultando a busca por categorias-----")

        val list = categoryRepository.findAll()

        val response = list.stream().map { category -> ListaCategoryResponse(category) }.collect(Collectors.toList())

        logger.info("---Listagem de categorias realizada com sucesso----")
        return response
    }
}