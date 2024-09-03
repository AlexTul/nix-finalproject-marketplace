package com.tuleninov.serverapi;

import com.tuleninov.serverapi.config.security.properties.CustomSecurityProperties;
import com.tuleninov.serverapi.model.auth.request.SignInRequest;
import com.tuleninov.serverapi.model.auth.response.AccessTokenResponse;
import com.tuleninov.serverapi.model.category.request.SaveCategoryRequest;
import com.tuleninov.serverapi.model.category.response.CategoryResponse;
import com.tuleninov.serverapi.model.user.request.SaveUserRequest;
import com.tuleninov.serverapi.repository.AuthorityRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties(CustomSecurityProperties.class)
class ServerApiApplicationTests {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    private static final Logger log = LoggerFactory.getLogger(ServerApiApplicationTests.class);

    @Autowired
    private TestRestTemplate rest;
    @Autowired
    private CustomSecurityProperties securityProperties;

    @Test
    void contextLoads() {
        assertNotNull(rest);
        log.info("TestRestTemplate is not null.");
    }

    // region user

    @Test
    void testLogin() {
        ResponseEntity<AccessTokenResponse> response = getLoginResponseEntity();

        assertEquals(HttpStatus.OK, response.getStatusCode());

        AccessTokenResponse accessTokenResponse = response.getBody();
        assertNotNull(accessTokenResponse);
        assertNotNull(accessTokenResponse.accessToken());
        assertNotNull(accessTokenResponse.refreshToken());
        assertEquals(getAccessExpireIn(), accessTokenResponse.expireIn());
        assertEquals(AuthorityRepository.ADMIN_AUTHORITIES, accessTokenResponse.authorities());
    }

    private long getAccessExpireIn() {
        return securityProperties.getJwt().getAccessExpireIn().getSeconds();
    }

    private ResponseEntity<AccessTokenResponse> getLoginResponseEntity() {
        SignInRequest body = getBody();
        HttpHeaders headers = getHeaders();
        HttpEntity<SignInRequest> requestEntity = new HttpEntity<>(body, headers);

        return rest.exchange(
                baseURLLogin(),
                HttpMethod.POST,
                requestEntity,
                AccessTokenResponse.class
        );
    }

    private SignInRequest getBody() {
        List<SaveUserRequest> userRequest = securityProperties.getAdmins().entrySet().stream()
                .map(entry -> new SaveUserRequest(
                        entry.getValue().getEmail(),
                        new String(entry.getValue().getPassword()),
                        entry.getKey()))
                .peek(admin -> log.info("Default admin found: {} <{}>", admin.nickname(), admin.email())).toList();

        return new SignInRequest(
                userRequest.get(0).email(),
                userRequest.get(0).password()
        );
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private URI baseURLLogin() {
        return URI.create(Routes.TOKEN);
    }

    // endregion user

    // region category

    @Test
    void testCreateCategory() {
        var name = "Cheese";
        ResponseEntity<CategoryResponse> categoryResponseEntity = createCategory(name);

        assertEquals(HttpStatus.CREATED, categoryResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, categoryResponseEntity.getHeaders().getContentType());

        CategoryResponse responseBody = categoryResponseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(name, responseBody.name());
    }

    private ResponseEntity<CategoryResponse> createCategory(String name) {
        URI url = baseUrlCategory();
        SaveCategoryRequest body = new SaveCategoryRequest(name);
        HttpHeaders headers = getHttpHeaders();
        HttpEntity<SaveCategoryRequest> requestEntity = new HttpEntity<>(body, headers);

        return rest.postForEntity(url, requestEntity, CategoryResponse.class);
    }

    private URI baseUrlCategory() {
        return URI.create(Routes.CATEGORIES);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getToken());
        return headers;
    }

    private String getToken() {
        ResponseEntity<AccessTokenResponse> response = getLoginResponseEntity();
        return Objects.requireNonNull(response.getBody()).accessToken();
    }

    // endregion category

}
