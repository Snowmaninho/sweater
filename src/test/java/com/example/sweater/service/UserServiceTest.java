package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;


@SpringBootTest // На самом деле это плохие Unit тесты, т.к. поднимают Spring контекст.
@ExtendWith(SpringExtension.class) // Аннотации @SpringBootTest, @Autowired, @MockBean используются для интеграционного тестирования
class UserServiceTest { // а тут должны быть @Mock, @InjectMocks без запуска всей программы

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;
    @MockBean
    private MailSender mailSender;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void addUser() {
        User user = new User();
        user.setEmail("some@some.ru");

        boolean isUserCreated = userService.addUser(user);

        Assertions.assertTrue(isUserCreated);
        Assertions.assertNotNull(user.getActivationCode());
        Assertions.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(userRepo, Mockito.times(1)).save(user); // проверка что userRepo был вызван 1 раз по методу save()
        Mockito.verify(mailSender, Mockito.times(1))
               .send(
                       ArgumentMatchers.eq(user.getEmail()),
                       ArgumentMatchers.eq("Activation code"),
                       ArgumentMatchers.contains("Welcome to Sweater.") // если строка постоянно меняется и мы не знаем о ней ничего
                                                                        // можно заменить на ArgumentMatchers.anyString()
               );
    }

    @Test
    void addUserFailTest() {
        User user = new User();
        user.setUsername("John");

        Mockito.doReturn(new User()) // Возвращаем нового пользователя
               .when(userRepo) // когда на userRepo
               .findByUsername("John"); // вызывается метод findByUsername с аргументом 'John'

        boolean isUserCreated = userService.addUser(user);

        Assertions.assertFalse(isUserCreated); // новый пользоватль не создался, т.к. уже существует пользователь с таким именем

        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class)); // проверяем что обращений небыло
        Mockito.verify(mailSender, Mockito.times(0))
               .send(
                       ArgumentMatchers.anyString(),
                       ArgumentMatchers.anyString(),
                       ArgumentMatchers.anyString()
               );
    }

    @Test
    void activateUser() {
        User user = new User();
        user.setActivationCode("bingo!");
        Mockito.doReturn(user)
               .when(userRepo)
               .findByActivationCode("activate");

        boolean isUserActivated = userService.activateUser("activate");

        Assertions.assertTrue(isUserActivated);
        Assertions.assertNull(user.getActivationCode());

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    void activateUserFailTest() {
        boolean isUserActivated = userService.activateUser("activate me");

        Assertions.assertFalse(isUserActivated);

        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }
}