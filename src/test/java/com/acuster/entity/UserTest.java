package com.acuster.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test methods in the User entity
 *
 * @author acuster
 */
public class UserTest {

    @Test
    void getAge() {

        User user = new User();

        LocalDate birthdate = LocalDate.parse("1986-10-18");
        user.setDateOfBirth(birthdate);

        int expectedAge = 34;

        int actualAge = user.getAge();

        assertEquals(expectedAge, actualAge);
    }

}
