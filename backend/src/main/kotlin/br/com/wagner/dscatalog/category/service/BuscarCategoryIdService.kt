package br.com.wagner.dscatalog.category.service

import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.response.BuscarCategoryIdResponse
import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BuscarCategoryIdService(@field:Autowired val categoryRepository: CategoryRepository) {

    val logger = LoggerFactory.getLogger(BuscarCategoryIdService::class.java)

    // metodo contendo a logica para buscar categoria por id

    @Transactional(readOnly = true)
    fun findById(id: Long): BuscarCategoryIdResponse {
        logger.info("---Execultando a busca de uma categoria por id $id-------")

        // validação

        val possivelCategory = categoryRepository.findById(id)

        if(possivelCategory.isEmpty) {
            logger.error("-----Entrou no if, Id $id de categoria não encontrado")
            throw ResourceNotFoundException("id de categoria não encontrada")
        }

        val category = possivelCategory.get()

        val response = BuscarCategoryIdResponse(category)

        logger.info("----Busca po id $id category realizada com sucesso-----")
        return response

    }
}