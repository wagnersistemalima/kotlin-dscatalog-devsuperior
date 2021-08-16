package br.com.wagner.dscatalog.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

// classe responsavel pela configuração do servidor de autorização

@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig(
    @field:Autowired val passwordEncoder: BCryptPasswordEncoder,
    @field:Autowired val acesssTokenConverter: JwtAccessTokenConverter,
    @field:Autowired val jwtTokenStore: JwtTokenStore,
    @field:Autowired val authenticationManager: AuthenticationManager

): AuthorizationServerConfigurerAdapter() {

    @field:Value("\${security.oauth2.client.client-id}")
    var clientId: String? = ""

    @field:Value("\${security.oauth2.client.client-secret}")
    var clientSecret: String? = ""

    @field:Value("\${jwt.duration}")
    var jwtDuration: Int? = null


    // configuração client api
    override fun configure(clients: ClientDetailsServiceConfigurer?) {

        clients!!.inMemory()
            .withClient(clientId)    // nome da aplicação
            .secret(passwordEncoder.encode(clientSecret))   // senha da aplicação
            .scopes("read", "write")                            // leitura e escrita
            .authorizedGrantTypes("password")
            .accessTokenValiditySeconds(jwtDuration!!)   // vence em 24 horas / 60 * 60 * 24
    }

    // configurar do processamento do token
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints!!.authenticationManager(authenticationManager)
            .tokenStore(jwtTokenStore)
            .accessTokenConverter(acesssTokenConverter)
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer?) {
        security!!.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
    }
}