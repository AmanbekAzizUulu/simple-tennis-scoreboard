package com.dandaev.edu.service;

import com.dandaev.edu.service.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final List<User> data;

    public UserService() {
        this.data = new ArrayList<>(List.of(
                new User("Aibek", 999L),
                new User("Eleonora", 1001L),
                new User("Azamat", 1002L),
                new User("Diana", 1003L),
                new User("Artem", 1004L),
                new User("Aigerim", 1005L),
                new User("Nursultan", 1006L),
                new User("Sofia", 996L),
                new User("Ilyas", 1008L),
                new User("Alina", 954L),
                new User("Timur", 1010L),
                new User("Kamila", 1011L),
                new User("Bektur", 1012L),
                new User("Madina", 1013L),
                new User("Erlan", 995L),
                new User("Anara", 1015L),
                new User("Ruslan", 1016L),
                new User("Zarina", 1017L),
                new User("Maksat", 909L),
                new User("Amina", 987L)
        ));
    }

    public List<User> getAllUsersByIdGreaterThen1000() {
        return data.stream()
                .filter(user -> user.id() > 1000)
                .toList();
    }

    public List<User> getAllUsers() {
        return data;
    }

    public void addUser(User user) {
        data.add(user);
    }
}
