package com.tuleninov.serverapi.controller;

import com.tuleninov.serverapi.Routes;
import com.tuleninov.serverapi.controller.user.UserController;
import com.tuleninov.serverapi.model.user.KnownAuthority;
import com.tuleninov.serverapi.model.user.UserStatus;
import com.tuleninov.serverapi.model.user.request.ChangeUserStatusRequest;
import com.tuleninov.serverapi.model.user.request.MergeUserRequest;
import com.tuleninov.serverapi.model.user.request.OverrideUserPasswordRequest;
import com.tuleninov.serverapi.model.user.request.SaveUserRequest;
import com.tuleninov.serverapi.model.user.response.PasswordResponse;
import com.tuleninov.serverapi.model.user.response.UserResponse;
import com.tuleninov.serverapi.service.user.UserOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mvc;
    private UserOperations userOperations;

    @BeforeEach
    void setUp() {
        userOperations = mock(UserOperations.class);
        mvc = MockMvcBuilders
                .standaloneSetup(new UserController(userOperations))
                .build();
    }

    @Test
    void testGetTemporaryPassword() throws Exception {
        var email = "test@gmail.com";
        var password = "fua9eeF1ahphooy5eth9ooth1iebee0AeWahyob4lai4cha3goohaewoh0gicieB";
        var passwordResponse = new PasswordResponse(password);
        var expectedJson = """
                {"password":"fua9eeF1ahphooy5eth9ooth1iebee0AeWahyob4lai4cha3goohaewoh0gicieB"}
                """;

        when(userOperations.createTemporaryPassword(email)).thenReturn(passwordResponse);

        mvc
                .perform(put(Routes.USERS + "/forgot")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email": "test@gmail.com"}
                                """))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

        verify(userOperations, only()).createTemporaryPassword(email);
    }

    @Test
    void testRegister() throws Exception {
        var code = "60e9202e-86f4-4b47-afef-9a8b9ee38f66";
        var id = 2L;
        var email = "test@gmail.com";
        var password = "fua9eeF1ahphooy5eth9ooth1iebee0AeWahyob4lai4cha3goohaewoh0gicieB";
        var nickname = "test";
        var request = new SaveUserRequest(email, password, nickname);
        var response = new UserResponse(
                id, email, nickname, UserStatus.SUSPENDED,
                OffsetDateTime.parse("2024-01-03T07:46:28.796Z"), Set.of(KnownAuthority.ROLE_USER));
        var expectedJson = """
                {"id": %s,"email": "test@gmail.com","nickname": "test","status": "SUSPENDED",
                "createdAt": "2024-01-03T07:46:28.796Z","authorities": ["ROLE_USER"],"path": "/api/v1/users/%s"}
                """.formatted(id, id);

        when(userOperations.create(request, code)).thenReturn(response);

        mvc
                .perform(post(Routes.USERS + "?code=" + code)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email": "test@gmail.com",
                                "password": "fua9eeF1ahphooy5eth9ooth1iebee0AeWahyob4lai4cha3goohaewoh0gicieB",
                                "nickname": "test"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

        verify(userOperations, only()).create(request, code);
    }

    @Test
    void testActivate() throws Exception {
        var code = "60e9202e-86f4-4b47-afef-9a8b9ee38f66";
        var id = 2L;
        var email = "test@gmail.com";
        var nickname = "test";
        var response = new UserResponse(
                id, email, nickname, UserStatus.ACTIVE,
                OffsetDateTime.parse("2024-01-03T07:46:28.796Z"), Set.of(KnownAuthority.ROLE_USER));
        var expectedJson = """
                {"id": %s,"email": "test@gmail.com","nickname": "test","status": "ACTIVE",
                "createdAt": "2024-01-03T07:46:28.796Z","authorities": ["ROLE_USER"],"path": "/api/v1/users/%s"}
                """.formatted(id, id);

        when(userOperations.activate(code)).thenReturn(response);

        mvc
                .perform(get(Routes.USERS + "/" + code + "/activate"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

        verify(userOperations, only()).activate(code);
    }

    @Test
    void testGetUserById() throws Exception {
        var id = 2L;
        var email = "test@gmail.com";
        var nickname = "test";
        var response = new UserResponse(
                id, email, nickname, UserStatus.ACTIVE,
                OffsetDateTime.parse("2024-01-03T07:46:28.796Z"), Set.of(KnownAuthority.ROLE_USER));
        var expectedJson = """
                {"id": %s,"email": "test@gmail.com","nickname": "test","status": "ACTIVE",
                "createdAt": "2024-01-03T07:46:28.796Z","authorities": ["ROLE_USER"],"path": "/api/v1/users/%s"}
                """.formatted(id, id);

        when(userOperations.findById(id)).thenReturn(Optional.of(response));

        mvc
                .perform(get(Routes.USERS + "/" + id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

        verify(userOperations, only()).findById(id);
    }

    @Test
    void testGetUserByAbsentId() throws Exception {
        var id = 2L;

        when(userOperations.findById(id)).thenReturn(Optional.empty());

        mvc
                .perform(get(Routes.USERS + "/" + id))
                .andExpect(status().isNotFound());

        verify(userOperations, only()).findById(id);
    }

    @Test
    void testRegisterAdmin() throws Exception {
        var code = "60e9202e-86f4-4b47-afef-9a8b9ee38f66";
        var id = 2L;
        var email = "test@gmail.com";
        var password = "fua9eeF1ahphooy5eth9ooth1iebee0AeWahyob4lai4cha3goohaewoh0gicieB";
        var nickname = "test";
        var request = new SaveUserRequest(email, password, nickname);
        var response = new UserResponse(
                id, email, nickname, UserStatus.SUSPENDED,
                OffsetDateTime.parse("2024-01-03T07:46:28.796Z"), Set.of(KnownAuthority.ROLE_ADMIN));
        var expectedJson = """
                {"id": %s,"email": "test@gmail.com","nickname": "test","status": "SUSPENDED",
                "createdAt": "2024-01-03T07:46:28.796Z","authorities": ["ROLE_ADMIN"],"path": "/api/v1/users/%s"}
                """.formatted(id, id);

        when(userOperations.createAdmin(request, code)).thenReturn(response);

        mvc
                .perform(post(Routes.USERS + "/admins?code=" + code)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email": "test@gmail.com",
                                "password": "fua9eeF1ahphooy5eth9ooth1iebee0AeWahyob4lai4cha3goohaewoh0gicieB",
                                "nickname": "test"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

        verify(userOperations, only()).createAdmin(request, code);
    }

    @Test
    void testMergeUserById() throws Exception {
        var id = 2L;
        var email = "test@gmail.com";
        var nickname = "test";
        var request = new MergeUserRequest(email, nickname);
        var response = new UserResponse(
                id, email, nickname, UserStatus.ACTIVE,
                OffsetDateTime.parse("2024-01-03T07:46:28.796Z"), Set.of(KnownAuthority.ROLE_USER));
        var expectedJson = """
                {"id": %s,"email": "test@gmail.com","nickname": "test","status": "ACTIVE",
                "createdAt": "2024-01-03T07:46:28.796Z","authorities": ["ROLE_USER"],"path": "/api/v1/users/%s"}
                """.formatted(id, id);

        when(userOperations.mergeById(id, request)).thenReturn(response);

        mvc
                .perform(put(Routes.USERS + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email": "test@gmail.com",
                                "nickname": "test"}
                                """))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

        verify(userOperations, only()).mergeById(id, request);
    }

    @Test
    void testChangeUserStatusById() throws Exception {
        var id = 2L;
        var email = "test@gmail.com";
        var nickname = "test";
        var request = new ChangeUserStatusRequest(UserStatus.ACTIVE);
        var response = new UserResponse(
                id, email, nickname, UserStatus.ACTIVE,
                OffsetDateTime.parse("2024-01-03T07:46:28.796Z"), Set.of(KnownAuthority.ROLE_USER));
        var expectedJson = """
                {"id": %s,"email": "test@gmail.com","nickname": "test","status": "ACTIVE",
                "createdAt": "2024-01-03T07:46:28.796Z","authorities": ["ROLE_USER"],"path": "/api/v1/users/%s"}
                """.formatted(id, id);

        when(userOperations.changeStatusById(id, request.status())).thenReturn(response);

        mvc
                .perform(put(Routes.USERS + "/" + id + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"status": "ACTIVE"}
                                """))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

        verify(userOperations, only()).changeStatusById(id, request.status());
    }

    @Test
    void testChangeUserPassword() throws Exception {
        var id = 2L;
        var email = "test@gmail.com";
        var password = "fua9eeF1ahphooy5eth9ooth1iebee0AeWahyob4lai4cha3goohaewoh0gicieB";
        var nickname = "test";
        var request = new OverrideUserPasswordRequest(password);
        var response = new UserResponse(
                id, email, nickname, UserStatus.ACTIVE,
                OffsetDateTime.parse("2024-01-03T07:46:28.796Z"), Set.of(KnownAuthority.ROLE_USER));
        var expectedJson = """
                {"id": %s,"email": "test@gmail.com","nickname": "test","status": "ACTIVE",
                "createdAt": "2024-01-03T07:46:28.796Z","authorities": ["ROLE_USER"],"path": "/api/v1/users/%s"}
                """.formatted(id, id);

        when(userOperations.changePasswordById(id, request)).thenReturn(response);

        mvc
                .perform(put(Routes.USERS + "/" + id + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"password": "fua9eeF1ahphooy5eth9ooth1iebee0AeWahyob4lai4cha3goohaewoh0gicieB"}
                                """))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

        verify(userOperations, only()).changePasswordById(id, request);
    }

    @Test
    void testDeleteUserById() throws Exception {
        var id = 2L;
        var email = "test@gmail.com";
        var nickname = "test";
        var response = new UserResponse(
                id, email, nickname, UserStatus.ACTIVE,
                OffsetDateTime.parse("2024-01-03T07:46:28.796Z"), Set.of(KnownAuthority.ROLE_USER));
        var expectedJson = """
                {"id": %s,"email": "test@gmail.com","nickname": "test","status": "ACTIVE",
                "createdAt": "2024-01-03T07:46:28.796Z","authorities": ["ROLE_USER"],"path": "/api/v1/users/%s"}
                """.formatted(id, id);

        when(userOperations.deleteById(id))
                .thenReturn(Optional.of(response))
                .thenReturn(Optional.empty());

        mvc
                .perform(delete(Routes.USERS + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        mvc
                .perform(delete(Routes.USERS + "/" + id))
                .andExpect(status().isNotFound());

        verify(userOperations, times(2)).deleteById(id);
        verifyNoMoreInteractions(userOperations);
    }
}
