package br.com.wagner.dscatalog.role.repository

import br.com.wagner.dscatalog.role.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository: JpaRepository<Role, Long> {
}