package br.com.wagner.dscatalog.user.controller

import br.com.wagner.dscatalog.user.request.InsertUserRequest
import br.com.wagner.dscatalog.user.service.InsertUserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/api/users")
class InsertUserController(@field:Autowired val insertUserService: InsertUserService) {

    val logger = LoggerFactory.getLogger(InsertUserController::class.java)

    // end pint para cadastrar um usuario

    @PostMapping
    fun insert(@Valid @RequestBody request: InsertUserRequest): ResponseEntity<Unit> {
        logger.info("----Iniciando cadastro de usuario------")

        val response = insertUserService.insert(request)

        val uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(response.id).toUri()

        return ResponseEntity.created(uri).build()
    }
}