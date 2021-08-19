package br.com.wagner.dscatalog.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.json.JacksonJsonParser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


@Component
class TokenUtil {

    @field:Value("\${security.oauth2.client.client-id}")
    var clientId: String? = ""

    @field:Value("\${security.oauth2.client.client-secret}")
    var clientSecret: String? = ""


    @Throws(Exception::class)
    fun obtainAccessToken(mockMvc: MockMvc, username: String?, password: String?): String? {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("grant_type", "password")
        params.add("client_id", clientId)
        params.add("username", username)
        params.add("password", password)
        val result = mockMvc
            .perform(MockMvcRequestBuilders.post("/oauth/token")
                    .params(params)
                    .with(httpBasic(clientId, clientSecret))
                    .accept("application/json;charset=UTF-8")
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        val resultString = result.andReturn().response.contentAsString
        val jsonParser = JacksonJsonParser()
        return jsonParser.parseMap(resultString).get("access_token").toString()
    }
}