package br.com.wagner.dscatalog.category.service

import br.com.wagner.dscatalog.category.repository.CategoryRepository
import br.com.wagner.dscatalog.handler.ExceptionGenericValidated
import br.com.wagner.dscatalog.handler.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteCategoryService(@field:Autowired val categoryRepository: CategoryRepository) {

    val logger = LoggerFactory.getLogger(DeleteCategoryService::class.java)

    // metodo contendo a logica para deletar uma categoria

    @Transactional
    fun delete(id: Long) {
        logger.info("---Execultando a deleção da categoria id $id-----")

        val obj = categoryRepository.findById(id)

        // validação

        if(obj.isEmpty) {
            logger.info("---Entrou no if, id $id da categoria não encontrado-----")
            throw ResourceNotFoundException("id da categoria não encontrado")
        }

        val category = obj.get()

        try {
            categoryRepository.delete(category)
        }
        catch (erro: DataIntegrityViolationException) {
            logger.info("Entrou no if, erro de integridade no banco na hora de deletar uma categoria")
            throw ExceptionGenericValidated("Integrity violation")
        }

        logger.info("----Categoria deletada com sucesso id $id-------")

    }


}