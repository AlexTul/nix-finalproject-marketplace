package com.tuleninov.web.feignclient;

import com.tuleninov.web.Routes;
import com.tuleninov.web.model.user.request.*;
import com.tuleninov.web.model.user.response.PasswordUIResponse;
import com.tuleninov.web.model.user.response.UserUIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Feign Client for the User.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@FeignClient(name = "UserController.class", url = "${services.server.api}")
public interface UserServiceFeignClient {

    /**
     * Get a new temporary password for the user.
     * Using PUT because Feign don`t support PATCH.
     *
     * @param request request with user`s parameters
     * @return temporary password
     */
    @PutMapping(
            value = Routes.API_USERS + "/forgot",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    PasswordUIResponse getTemporaryPassword(@RequestBody ForgotPasswordUIRequest request);

    /**
     * Register user as client in database.
     *
     * @param request request with user`s parameters
     * @param code    activation code
     * @return user who was registered in response format
     */
    @PostMapping(
            value = Routes.API_USERS,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    UserUIResponse register(@RequestBody SaveUserUIRequest request,
                            @RequestParam String code);

    /**
     * Activate the user in database.
     *
     * @param code activation code
     * @return user that has been activated in response format
     */
    @GetMapping(
            value = Routes.API_USERS + "/{code}/activate",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    UserUIResponse activate(@PathVariable String code);

    /**
     * Get the user by email from the database in response format.
     *
     * @param token token to access the corresponding endpoint
     * @return current user from the database in response format
     */
    @GetMapping(
            value = Routes.API_USERS + "/me",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    UserUIResponse getCurrentUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Merge current user in database.
     * Using PUT because Feign don`t support PATCH.
     *
     * @param token   token to access the corresponding endpoint
     * @param request request with user credentials
     * @return user that was merged
     */
    @PutMapping(
            value = Routes.API_USERS + "/me",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    UserUIResponse mergeCurrentUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                    @RequestBody MergeUserUIRequest request);

    /**
     * Change current user`s password in database.
     * Using PUT because Feign don`t support PATCH.
     *
     * @param token   token to access the corresponding endpoint
     * @param request request with user credentials
     * @return user who changed the password in response format
     */
    @PutMapping(
            value = Routes.API_USERS + "/me/password",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    UserUIResponse changeCurrentUserPassword(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                             @RequestBody ChangeUserUIPasswordRequest request);

    /**
     * Delete the current user in database.
     *
     * @param token token to access the corresponding endpoint
     */
    @DeleteMapping(
            value = Routes.API_USERS + "/me"
    )
    void deleteCurrentUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    /**
     * Get the user by id from the database in response format.
     *
     * @param token token to access the corresponding endpoint
     * @param id    id of user
     * @return the user from database in response format
     */
    @GetMapping(
            value = Routes.API_USERS + "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    UserUIResponse getUserById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                               @PathVariable long id);

    /**
     * Get all users from database in response format with pagination information.
     *
     * @param token    token to access the corresponding endpoint
     * @param pageable abstract interface for pagination information
     * @return all users from database in response format
     */
    @GetMapping(
            value = Routes.API_USERS + "/admins",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Page<UserUIResponse> listUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                   Pageable pageable);

    /**
     * Register user as admin in database.
     *
     * @param request request with new admin data
     */
    @PostMapping(
            value = Routes.API_USERS + "/admins",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    UserUIResponse registerAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                 @RequestBody SaveUserUIRequest request,
                                 @RequestParam String code);

    /**
     * Merge user by id in database.
     * Using PUT because Feign don`t support PATCH.
     *
     * @param token   token to access the corresponding endpoint
     * @param id      id of user
     * @param request request with user credentials
     * @return user that was merged in response format
     */
    @PutMapping(
            value = Routes.API_USERS + "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    UserUIResponse mergeUserById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                 @PathVariable long id,
                                 @RequestBody MergeUserUIRequest request);

    /**
     * Change user`s status by id in database.
     * Using PUT because Feign don`t support PATCH.
     *
     * @param token   token to access the corresponding endpoint
     * @param id      id of user
     * @param request request with user credentials
     * @return user whose status was changed in response format
     */
    @PutMapping(
            value = Routes.API_USERS + "/{id}/status",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    UserUIResponse changeUserStatusById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                        @PathVariable long id,
                                        @RequestBody ChangeUserUIStatusRequest request);

    /**
     * Change user`s password by id in database.
     * Using PUT because Feign don`t support PATCH.
     *
     * @param token   token to access the corresponding endpoint
     * @param id      id of user
     * @param request request with user credentials
     * @return user whose password was changed in response format
     */
    @PutMapping(
            value = Routes.API_USERS + "/{id}/password",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    UserUIResponse changeUserPassword(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                      @PathVariable long id,
                                      @RequestBody OverrideUserUIPasswordRequest request);

    /**
     * Delete user by id in the database.
     *
     * @param token token to access the corresponding endpoint
     * @param id    id of user
     * @return user what was deleted in response format
     */
    @DeleteMapping(
            value = Routes.API_USERS + "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    UserUIResponse deleteById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                              @PathVariable long id);
}
