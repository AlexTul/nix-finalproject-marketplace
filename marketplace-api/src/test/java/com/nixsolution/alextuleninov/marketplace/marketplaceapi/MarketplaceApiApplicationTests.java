package com.nixsolution.alextuleninov.marketplace.marketplaceapi;

import com.nixsolution.alextuleninov.marketplace.marketplaceapi.data.category.CategoryResponse;
import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.category.SaveCategoryRequest;
import com.nixsolution.alextuleninov.marketplace.marketplaceapi.data.goods.GoodsResponse;
import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.goods.SaveGoodsRequest;
import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.user.SaveUserRequest;
import com.nixsolution.alextuleninov.marketplace.marketplaceapi.data.user.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"h2db", "debug"})
class MarketplaceApiApplicationTests {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void contextLoads() {
        assertNotNull(rest);
    }

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

    @Test
    void testCreateInvalidCategory() {
        ResponseEntity<?> blankNameResponse = createCategory("");
        assertEquals(HttpStatus.BAD_REQUEST, blankNameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, blankNameResponse.getHeaders().getContentType());

        ResponseEntity<?> size1NameResponse = createCategory("q");
        assertEquals(HttpStatus.BAD_REQUEST, size1NameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, size1NameResponse.getHeaders().getContentType());

        ResponseEntity<?> size31NameResponse = createCategory("qwertyuiopasdfghjklzxcvbnmqwert");
        assertEquals(HttpStatus.BAD_REQUEST, size31NameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, size31NameResponse.getHeaders().getContentType());

        ResponseEntity<?> noNameResponse = createCategory(null);
        assertEquals(HttpStatus.BAD_REQUEST, noNameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, noNameResponse.getHeaders().getContentType());
    }

    @Test
    void testGetCategoryById() {
        var name = "CheeseForGetCategoryById";

        var categoryResponse = createCategory(name).getBody();
        assertNotNull(categoryResponse);

        int id = categoryResponse.id();

        var categoryUrl = baseUrlCategory(id);

        ResponseEntity<CategoryResponse> categoryResponseEntity = rest.getForEntity(categoryUrl, CategoryResponse.class);
        assertEquals(HttpStatus.OK, categoryResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, categoryResponseEntity.getHeaders().getContentType());

        CategoryResponse responseBody = categoryResponseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(name, responseBody.name());
        assertEquals(id, responseBody.id());

        assertEquals(responseBody, rest.getForEntity(categoryUrl, CategoryResponse.class).getBody());
    }

    @Test
    void testGetNonExistingCategory() {
        var categoryUrl = baseUrlCategory(new Random().nextInt());

        ResponseEntity<CategoryResponse> categoryResponseEntity = rest.getForEntity(categoryUrl, CategoryResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, categoryResponseEntity.getStatusCode());
    }

    @Test
    void testUpdateCategory() {
        var name = "CheeseForUpdateCategory";

        var categoryResponse = createCategory(name).getBody();
        assertNotNull(categoryResponse);

        int id = categoryResponse.id();

        var categoryUrl = baseUrlCategory(id);

        var updatedName = "UpdatingCheeseForUpdateCategory";

        rest.put(categoryUrl, new SaveCategoryRequest(updatedName));

        ResponseEntity<CategoryResponse> categoryResponseEntity = rest.getForEntity(categoryUrl, CategoryResponse.class);
        assertEquals(HttpStatus.OK, categoryResponseEntity.getStatusCode());
    }

    @Test
    void testDeleteCategory() {
        var name = "CheeseForDeleteCategory";

        var categoryResponse = createCategory(name).getBody();
        assertNotNull(categoryResponse);

        int id = categoryResponse.id();

        var categoryUrlAdmins = baseUrlCategoryAdmins(id);

        ResponseEntity<CategoryResponse> categoryResponseEntity = rest.exchange(
                RequestEntity.delete(categoryUrlAdmins).build(), CategoryResponse.class);

        assertEquals(HttpStatus.NO_CONTENT, categoryResponseEntity.getStatusCode());
    }

    private ResponseEntity<CategoryResponse> createCategory(String name) {
        var url = baseUrlCategory();
        var requestBody = new SaveCategoryRequest(name);

        return rest.postForEntity(url, requestBody, CategoryResponse.class);
    }

    private URI baseUrlCategory() {
        return URI.create("/admins/categories");
    }

    private URI baseUrlCategory(int id) {
        return new UriTemplate("/categories/{id}").expand(id);
    }

    private URI baseUrlCategoryAdmins(int id) {
        return new UriTemplate("/admins/categories/{id}").expand(id);
    }
    // endregion category

    // region goods
    @Test
    void testCreateGoods() {
        var category = createCategory("testCreateGoods").getBody();

        var name = "Goods";
        assert category != null;
        var categoryId = category.id();
        var price = 21.0;
        var weight = 44;
        var description = "Very testy each";
        var imageName = "goods.png";

        ResponseEntity<GoodsResponse> goodsResponseEntity = createGoods(name, categoryId, price,
                weight, description, imageName);

        assertEquals(HttpStatus.CREATED, goodsResponseEntity.getStatusCode()); // 404 , null
        assertEquals(MediaType.APPLICATION_JSON, goodsResponseEntity.getHeaders().getContentType());

        GoodsResponse responseBody = goodsResponseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(name, responseBody.name());
        assertEquals(categoryId, responseBody.categoryId());
        assertEquals(price, responseBody.price());
        assertEquals(weight, responseBody.weight());
        assertEquals(description, responseBody.description());
        assertEquals(imageName, responseBody.imageName());
    }

    @Test
    void testCreateInvalidGoods() {
        ResponseEntity<?> blankNameResponse = createGoods(
                "", 1, 1.1, 100, "Very testy goods", "goods.png");
        assertEquals(HttpStatus.BAD_REQUEST, blankNameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, blankNameResponse.getHeaders().getContentType());

        ResponseEntity<?> size1NameResponse = createGoods(
                "q", 1, 1.1, 100, "Very testy goods", "goods.png");
        assertEquals(HttpStatus.BAD_REQUEST, size1NameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, size1NameResponse.getHeaders().getContentType());

        ResponseEntity<?> size31NameResponse = createGoods(
                "qwertyuiopasdfghjklzxcvbnmqwerts", 1, 1.1, 100,
                "Very testy goods", "goods.png");
        assertEquals(HttpStatus.BAD_REQUEST, size31NameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, size31NameResponse.getHeaders().getContentType());

        ResponseEntity<?> noNameResponse = createGoods(null, 1, 1.1, 100,
                "Very testy goods", "goods.png");
        assertEquals(HttpStatus.BAD_REQUEST, noNameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, noNameResponse.getHeaders().getContentType());

        ResponseEntity<?> negativeCategoryIdResponse = createGoods(
                "Banana", -1, 1.1, 100,
                "Very testy goods", "goods.png");
        assertEquals(HttpStatus.BAD_REQUEST, negativeCategoryIdResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, negativeCategoryIdResponse.getHeaders().getContentType());

        ResponseEntity<?> negativePriceResponse = createGoods(
                "Banana", 1, -1.1, 100,
                "Very testy goods", "goods.png");
        assertEquals(HttpStatus.BAD_REQUEST, negativePriceResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, negativePriceResponse.getHeaders().getContentType());

        ResponseEntity<?> negativeWeightResponse = createGoods(
                "Banana", 1, 1.1, -100,
                "Very testy goods", "goods.png");
        assertEquals(HttpStatus.BAD_REQUEST, negativeWeightResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, negativeWeightResponse.getHeaders().getContentType());

        ResponseEntity<?> blankDescriptionResponse = createGoods(
                "Banana", 1, 1.1, 100, "", "goods.png");
        assertEquals(HttpStatus.BAD_REQUEST, blankDescriptionResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, blankDescriptionResponse.getHeaders().getContentType());

        ResponseEntity<?> size1DescriptionResponse = createGoods(
                "Banana", 1, 1.1, 100, "a", "goods.png");
        assertEquals(HttpStatus.BAD_REQUEST, size1DescriptionResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, size1DescriptionResponse.getHeaders().getContentType());

        ResponseEntity<?> noDescriptionResponse = createGoods("Banana", 1, 1.1, 100,
                null, "goods.png");
        assertEquals(HttpStatus.BAD_REQUEST, noDescriptionResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, noDescriptionResponse.getHeaders().getContentType());

        ResponseEntity<?> size1ImageNameResponse = createGoods(
                "Banana", 1, 1.1, 100, "Very testy goods", "a");
        assertEquals(HttpStatus.BAD_REQUEST, size1ImageNameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, size1ImageNameResponse.getHeaders().getContentType());

        ResponseEntity<?> size31ImageNameResponse = createGoods(
                "Banana", 1, 1.1, 100, "Very testy goods",
                "qwertyuiopasdfghjklzxcvbnsmqwert");
        assertEquals(HttpStatus.BAD_REQUEST, size31ImageNameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, size31ImageNameResponse.getHeaders().getContentType());
    }

    @Test
    void testGetGoodsById() {
        var category = createCategory("testGetGoodsById").getBody();

        var name = "GoodsById";
        assert category != null;
        var categoryId = category.id();
        var price = 20.0;
        var weight = 47;
        var description = "Very testy GoodsById";
        var imageName = "GoodsById.png";

        var goodsResponse = createGoods(name, categoryId, price,
                weight, description, imageName).getBody();
        assertNotNull(goodsResponse);

        int id = goodsResponse.id();

        var goodsUrl = baseUrlGoods(id);

        ResponseEntity<GoodsResponse> goodsResponseEntity = rest.getForEntity(goodsUrl, GoodsResponse.class);
        assertEquals(HttpStatus.OK, goodsResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, goodsResponseEntity.getHeaders().getContentType());

        GoodsResponse responseBody = goodsResponseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(name, responseBody.name());
        assertEquals(categoryId, responseBody.categoryId());
        assertEquals(price, responseBody.price());
        assertEquals(weight, responseBody.weight());
        assertEquals(description, responseBody.description());
        assertEquals(imageName, responseBody.imageName());
        assertEquals(id, responseBody.id());

        assertEquals(responseBody, rest.getForEntity(goodsUrl, GoodsResponse.class).getBody());
    }

    @Test
    void testGetNonExistingGoods() {
        var goodsUrl = baseUrlGoods(new Random().nextInt());

        ResponseEntity<GoodsResponse> goodsResponseEntity = rest.getForEntity(goodsUrl, GoodsResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, goodsResponseEntity.getStatusCode());
    }

    @Test
    void testUpdateGoods() {
        var category = createCategory("testUpdateGoods").getBody();

        var name = "GoodsUpdate";
        assert category != null;
        var categoryId = category.id();
        var price = 20.5;
        var weight = 49;
        var description = "Very testy GoodsUpdate";
        var imageName = "GoodsUpdate.png";

        var goodsResponse = createGoods(name, categoryId, price,
                weight, description, imageName).getBody();
        assertNotNull(goodsResponse);

        int id = goodsResponse.id();

        var goodsUrl = baseUrlGoods(id);

        var updatedName = "UpdatingGoodsUpdate";
        var updatedCategoryId = 2;
        var updatedPrice = 31.01;
        var updatedWeight = 50;
        var updatedDescription = "Updating very testy UpdatingGoodsUpdate";
        var updatedImageName = "UpdatingGoodsUpdate.png";

        rest.put(goodsUrl, new SaveGoodsRequest(updatedName, updatedCategoryId, updatedPrice,
                updatedWeight, updatedDescription, updatedImageName));

        ResponseEntity<GoodsResponse> goodsResponseEntity = rest.getForEntity(goodsUrl, GoodsResponse.class);
        assertEquals(HttpStatus.OK, goodsResponseEntity.getStatusCode());
    }

    @Test
    void testDeleteGoods() {
        var category = createCategory("testDeleteGoods").getBody();

        var name = "GoodsDelete";
        assert category != null;
        var categoryId = category.id();
        var price = 25.0;
        var weight = 52;
        var description = "Very testy GoodsDelete";
        var imageName = "GoodsDelete.png";

        var goodsResponse = createGoods(name, categoryId, price,
                weight, description, imageName).getBody();
        assertNotNull(goodsResponse);

        int id = goodsResponse.id();

        var goodsUrl = baseUrlGoods(id);
        var goodsUrlAdmins = baseUrlGoodsAdmins(id);

        ResponseEntity<GoodsResponse> goodsResponseEntity = rest
                .exchange(RequestEntity.delete(goodsUrlAdmins).build(), GoodsResponse.class);

        assertEquals(HttpStatus.OK, goodsResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, goodsResponseEntity.getHeaders().getContentType());

        GoodsResponse responseBody = goodsResponseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(name, responseBody.name());
        assertEquals(categoryId, responseBody.categoryId());
        assertEquals(price, responseBody.price());
        assertEquals(weight, responseBody.weight());
        assertEquals(description, responseBody.description());
        assertEquals(imageName, responseBody.imageName());
        assertEquals(id, responseBody.id());

        assertEquals(HttpStatus.NOT_FOUND, rest.getForEntity(goodsUrl, GoodsResponse.class).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, rest
                .exchange(RequestEntity.delete(goodsUrlAdmins).build(), GoodsResponse.class)
                .getStatusCode());
    }

    private ResponseEntity<GoodsResponse> createGoods(String name, int categoryId, double price,
                                                      int weight, String description, String imageName) {
        var url = baseUrlGoods();
        var requestBody = new SaveGoodsRequest(name, categoryId, price, weight, description, imageName);

        return rest.postForEntity(url, requestBody, GoodsResponse.class);
    }

    private URI baseUrlGoods() {
        return URI.create("/admins/goods");
    }

    private URI baseUrlGoods(int id) {
        return new UriTemplate("/goods/{id}").expand(id);
    }

    private URI baseUrlGoodsAdmins(int id) {
        return new UriTemplate("/admins/goods/{id}").expand(id);
    }
    // endregion goods

    // region user
    @Test
    void testCreateUser() {
        var firstName = "User";
        var lastName = "Free";
        var email = "user@gmail.com";
        var password = "1234567890";

        ResponseEntity<UserResponse> userResponseEntity = createUser(firstName, lastName, email, password);

        assertEquals(HttpStatus.CREATED, userResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, userResponseEntity.getHeaders().getContentType());

        UserResponse responseBody = userResponseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(firstName, responseBody.firstName());
        assertEquals(lastName, responseBody.lastName());
        assertEquals(email, responseBody.email());
    }

    @Test
    void testCreateInvalidUser() {
        ResponseEntity<?> blankFirstNameResponse = createUser(
                "", "Walker", "walker@gmail.com", "1234567890");
        assertEquals(HttpStatus.BAD_REQUEST, blankFirstNameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, blankFirstNameResponse.getHeaders().getContentType());

        ResponseEntity<?> size1FirstNameResponse = createUser(
                "S", "Walker", "walker@gmail.com", "1234567890");
        assertEquals(HttpStatus.BAD_REQUEST, size1FirstNameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, size1FirstNameResponse.getHeaders().getContentType());

        ResponseEntity<?> size31FirstNameResponse = createUser(
                "qwertyuiopasdfghjklzxcvbnmqwert",
                "Walker", "walker@gmail.com", "1234567890");
        assertEquals(HttpStatus.BAD_REQUEST, size31FirstNameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, size31FirstNameResponse.getHeaders().getContentType());

        ResponseEntity<?> noFirstNameResponse = createUser(
                null, "Walker", "walker@gmail.com", "1234567890");
        assertEquals(HttpStatus.BAD_REQUEST, noFirstNameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, noFirstNameResponse.getHeaders().getContentType());

        ResponseEntity<?> size1LastNameResponse = createUser(
                "Joe", "a", "walker@gmail.com", "1234567890");
        assertEquals(HttpStatus.BAD_REQUEST, size1LastNameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, size1LastNameResponse.getHeaders().getContentType());

        ResponseEntity<?> size31LastNameResponse = createUser(
                "Joe",
                "qwertyuiopasdfghjklzxcvbnmqwert", "walker@gmail.com", "1234567890");
        assertEquals(HttpStatus.BAD_REQUEST, size31LastNameResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, size31LastNameResponse.getHeaders().getContentType());

        ResponseEntity<?> blankEmailResponse = createUser(
                "Joe", "Walker", "", "1234567890");
        assertEquals(HttpStatus.BAD_REQUEST, blankEmailResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, blankEmailResponse.getHeaders().getContentType());

        ResponseEntity<?> incorrectEmailResponse = createUser(
                "Joe", "Walker", "gmail", "1234567890");
        assertEquals(HttpStatus.BAD_REQUEST, incorrectEmailResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, incorrectEmailResponse.getHeaders().getContentType());

        ResponseEntity<?> noEmailResponse = createUser(
                "Joe", "Walker", null, "1234567890");
        assertEquals(HttpStatus.BAD_REQUEST, noEmailResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, noEmailResponse.getHeaders().getContentType());

        ResponseEntity<?> blankPasswordResponse = createUser(
                "Joe", "Walker", "walker@gmail.com", "");
        assertEquals(HttpStatus.BAD_REQUEST, blankPasswordResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, blankPasswordResponse.getHeaders().getContentType());

        ResponseEntity<?> size1PasswordResponse = createUser(
                "Joe", "Walker", "walker@gmail.com", "1");
        assertEquals(HttpStatus.BAD_REQUEST, size1PasswordResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, size1PasswordResponse.getHeaders().getContentType());

        ResponseEntity<?> size35PasswordResponse = createUser(
                "Joe", "Walker", "walker@gmail.com", "12345678901234567890123456789012345");
        assertEquals(HttpStatus.BAD_REQUEST, size35PasswordResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, size35PasswordResponse.getHeaders().getContentType());

        ResponseEntity<?> noPasswordResponse = createUser(
                "Joe", "Walker", "walker@gmail.com", null);
        assertEquals(HttpStatus.BAD_REQUEST, noPasswordResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, noPasswordResponse.getHeaders().getContentType());
    }

    @Test
    void testGetUserById() {
        var firstName = "UserById";
        var lastName = "FreeUserById";
        var email = "UserById@gmail.com";
        var password = "1235567890";

        var userResponse = createUser(firstName, lastName, email, password).getBody();
        assertNotNull(userResponse);

        int id = userResponse.id();

        var userUrl = baseUrlUserGet(id);

        ResponseEntity<UserResponse> userResponseEntity = rest.getForEntity(userUrl, UserResponse.class);
        assertEquals(HttpStatus.OK, userResponseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, userResponseEntity.getHeaders().getContentType());

        UserResponse responseBody = userResponseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(firstName, responseBody.firstName());
        assertEquals(lastName, responseBody.lastName());
        assertEquals(email, responseBody.email());
        assertEquals(id, responseBody.id());

        assertEquals(responseBody, rest.getForEntity(userUrl, UserResponse.class).getBody());
    }

    @Test
    void testGetNonExistingUser() {
        var userUrl = baseUrlUser(new Random().nextInt());

        ResponseEntity<UserResponse> userResponseEntity = rest.getForEntity(userUrl, UserResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, userResponseEntity.getStatusCode());
    }

    @Test
    void testUpdateUser() {
        var firstName = "UserUpdate";
        var lastName = "FreeUserUpdate";
        var email = "UserUpdate@gmail.com";
        var password = "1334567890";

        var userResponse = createUser(firstName, lastName, email, password).getBody();
        assertNotNull(userResponse);

        int id = userResponse.id();

        var userUrlUpdate = baseUrlUser(id);

        var updatedFirstName = "UpdatingUserUpdate";
        var updatedLastName = "UpdatingFreeUserUpdate";
        var updatedEmail = "updatingUserUpdate@gmail.com";
        var updatedPassword = "0987653321";

        rest.put(userUrlUpdate, new SaveUserRequest(updatedFirstName, updatedLastName, updatedEmail, updatedPassword));

        var userUrlGet = baseUrlUserGet(id);
        ResponseEntity<UserResponse> userResponseEntity = rest.getForEntity(userUrlGet, UserResponse.class);
        assertEquals(HttpStatus.OK, userResponseEntity.getStatusCode());
    }

    @Test
    void testDeleteUser() {
        var firstName = "UserDelete";
        var lastName = "FreeUserDelete";
        var email = "UserDelete@gmail.com";
        var password = "1234567990";

        var userUrl = baseUrlUser(email);

        var userResponse = createUser(firstName, lastName, email, password).getBody();
        assertNotNull(userResponse);

        ResponseEntity<UserResponse> userResponseEntity = rest.exchange(
                RequestEntity.delete(userUrl).build(), UserResponse.class);

        assertEquals(HttpStatus.NO_CONTENT, userResponseEntity.getStatusCode());
    }

    private ResponseEntity<UserResponse> createUser(String firstName, String lastName,
                                                    String email, String password) {
        var url = baseUrlUser();
        var requestBody = new SaveUserRequest(firstName, lastName, email, password);

        return rest.postForEntity(url, requestBody, UserResponse.class);
    }

    private URI baseUrlUser() {
        return URI.create("/register");
    }

    private URI baseUrlUserGet(int id) {
        return new UriTemplate("/users/{id}").expand(id);
    }

    private URI baseUrlUser(int id) {
        return new UriTemplate("/admins/users/{id}").expand(id);
    }

    private URI baseUrlUser(String email) {
        return new UriTemplate("/admins/users/{email}").expand(email);
    }
    // endregion user
}
