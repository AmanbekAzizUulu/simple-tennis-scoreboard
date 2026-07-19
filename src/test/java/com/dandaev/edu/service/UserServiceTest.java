package com.dandaev.edu.service;

import com.dandaev.edu.service.entities.User;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        System.out.println("void setUp() ─ method annotated with @BeforeEach ");
        System.out.println(this);
        userService = new UserService();
    }

    @Test
    @DisplayName("Should return all users from database")
    void shouldReturnUsersWithIDGreaterThen1000() {
        System.out.println("void shouldReturnUsersWithIDGreaterThen1000() ─ testing method annotated with @Test");

        List<User> users = userService.getAllUsersByIdGreaterThen1000();
        List<Long> expectedIds = List.of(1001L, 1002L, 1003L, 1004L, 1005L, 1006L, 1008L, 1010L, 1011L, 1012L, 1013L, 1015L, 1016L, 1017L);

        List<Long> actualIds = users.stream().map(User::id).toList();

        assertEquals(expectedIds, actualIds);
    }

    @Test
    void shouldSizeIncreasedWhenUserAdded() {
        System.out.println("void shouldSizeIncreasedWhenUserAdded() ─ testing method annotated with @Test");

        int initialSizeOfListOfUsers = userService.getAllUsers().size();

        User user = new User("Stranger", 1041L);

        userService.addUser(user);

        int sizeOfListOfUsersAfterAddingNewOne = userService.getAllUsers().size();

        assertNotEquals(initialSizeOfListOfUsers, sizeOfListOfUsersAfterAddingNewOne);
    }

    @AfterEach
    void releaseResources(){
        System.out.println("void releaseResources() ─ method annotated with @AfterEach");
        System.out.println(this);
    }
}
