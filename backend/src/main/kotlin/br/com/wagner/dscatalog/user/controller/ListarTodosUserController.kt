package br.com.wagner.dscatalog.user.controller

import br.com.wagner.dscatalog.user.response.ListaUserResponse
import br.com.wagner.dscatalog.user.service.ListaUserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class ListarTodosUserController(@field:Autowired val listaUserService: ListaUserService) {

    val logger = LoggerFactory.getLogger(ListarTodosUserController::class.java)

    // end point para listar usuarios

    @GetMapping
    fun findAll(
        @RequestParam(value = "page", defaultValue = "0",) page: Int,
        @RequestParam(value = "linesPerPage", defaultValue = "12") linesPerPage: Int,
        @RequestParam(value = "direction", defaultValue = "ASC") direction: String,
        @RequestParam(value = "orderBy", defaultValue = "fistName") orderBy: String
    ) : ResponseEntity<Page<ListaUserResponse>> {
        logger.info("-----Iniciando a listagem de usuarios-----")

        val pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy)

        val response = listaUserService.findAll(pageRequest)

        return ResponseEntity.ok().body(response)
    }
}