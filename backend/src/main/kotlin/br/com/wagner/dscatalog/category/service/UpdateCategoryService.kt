package br.com.wagner.dscatalog.category.service

import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.category.request.UpdateCategoryRequest
import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateCategoryService(@field:Autowired val categoryRepository: CategoryRepository) {

    val logger = LoggerFactory.getLogger(UpdateCategoryService::class.java)

    // metodo contendo a logica para atualizar uma categoria por id

    @Transactional
    fun update(id: Long, request: UpdateCategoryRequest) {
        logger.info("---Execultando a atualização de uma categoria id $id-------")

        val obj = categoryRepository.findById(id)

        // validação

        if(obj.isEmpty) {
            logger.error("---Caio no if, id da categoria não encontrado-----")
            throw ResourceNotFoundException("id da categoria não encontrado")
        }

        val category = obj.get()

        category.name = request.name
        category.update()
        categoryRepository.save(category)
        logger.info("----Atualização da categoria id $id realizada com sucesso------")

    }
}