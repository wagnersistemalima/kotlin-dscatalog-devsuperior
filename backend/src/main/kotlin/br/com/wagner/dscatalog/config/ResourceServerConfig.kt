package br.com.wagner.dscatalog.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import java.util.*


// classe responsavel por validar o token e as permisoes do usuario para acessar as rotas

@Configuration
@EnableResourceServer
class ResourceServerConfig(
    @field:Autowired val tokenStore: JwtTokenStore,
    @field:Autowired val env: Environment
): ResourceServerConfigurerAdapter() {

    private val public = "/oauth/token"

    private val OPERATOR_OR_ADMIN: Array<String> = arrayOf("/api/products/**", "/api/categories/**")

    private val ADMIN: Array<String> = arrayOf("/api/users/**")

    // configuraçao token

    override fun configure(resources: ResourceServerSecurityConfigurer?) {
        resources!!.tokenStore(tokenStore)
    }

    // configurar das rotas / crud de produtos e categorias permissao operador / crud usuarios permisao admin

    override fun configure(http: HttpSecurity?) {


        // validaçao para liberar end point banco de teste h2
        if (env.activeProfiles.contains("test")) {
            http!!.headers().frameOptions().disable()
        }

        http!!.authorizeRequests()
            .antMatchers("/oauth/token").permitAll() // publico
            .antMatchers(HttpMethod.GET, "/api/products/**").permitAll()    // publico
            .antMatchers(HttpMethod.GET, "/api/categories/**").permitAll()  // publico
            .antMatchers("/h2-console/**").permitAll()                      // publico h2
            .antMatchers("/api/products").hasRole("OPERATOR")       // operador
            .antMatchers("/api/products/**").hasRole("OPERATOR")       // operador
            .antMatchers("/api/categories").hasRole("OPERATOR")     // operador
            .antMatchers("/api/categories/**").hasRole("OPERATOR")     // operador
            .antMatchers("/api/products/**").hasRole("ADMIN")       // admin
            .antMatchers("/api/categories/**").hasRole("ADMIN")     // admin
            .antMatchers("/api/users/**").hasRole("ADMIN")          // admin
            .anyRequest().authenticated()

    }

}