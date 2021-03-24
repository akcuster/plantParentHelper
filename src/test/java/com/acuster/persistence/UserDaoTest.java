package com.acuster.persistence;

import com.acuster.entity.Plant;
import com.acuster.entity.User;
import com.acuster.test.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type User dao test.
 */
class UserDaoTest {

    GenericDao genericDao;

    /**
     * Sets up by instantiating UserDao class.
     */
    @BeforeEach
    void setUp() {

        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        genericDao = new GenericDao(User.class);
    }

    /**
     * Verifies gets all users successfully.
     */
    @Test
    void getAllUsersSuccess() {
        List<User> users = (List<User>) genericDao.getAll();
        assertEquals(6, users.size());
    }

    /**
     * Verifies gets users by last name successfully.
     */
    @Test
    void getByLastNameSuccess() {
        List<User> users = (List<User>)genericDao.getByLastName("c");
        assertEquals(3, users.size());
    }

    /**
     * Verifies a user is returned correctly base on id search.
     */
    @Test
    void getUsersByIdSuccess() {
        User retrievedUser = (User)genericDao.getById(3);
        assertNotNull(retrievedUser);
        assertEquals("Barney", retrievedUser.getFirstName());

    }

    /**
     * Verify successful insert of a user
     */
    @Test
    void insertSuccess() {

        User newUser = new User( "fflintstone", "yabbadabbadoo", "Fred", "Flintstone", LocalDate.parse("1968-01-01"));
        int id = genericDao.insert(newUser);
        assertNotEquals(0,id);
        User insertedUser = (User)genericDao.getById(id);
        assertEquals(newUser, insertedUser);
    }


    /**
     * Verify successful delete of user
     */
    @Test
    void deleteSuccess() {
        genericDao.delete(genericDao.getById(3));
        assertNull(genericDao.getById(3));
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyEqualSuccess() {
        List<User> users = (List<User>)genericDao.getByPropertyEqual("lastName", "Curry");
        assertEquals(1, users.size());
        assertEquals(3, users.get(0).getId());
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<User> users = (List<User>)genericDao.getByPropertyLike("lastName", "c");
        assertEquals(3, users.size());
    }

    /**
     * Verify successful update of user.
     */
    @Test
    void updateSuccess() {
        String newLastName = "Davis";
        User userToUpdate = (User)genericDao.getById(3);
        userToUpdate.setLastName(newLastName);
        genericDao.saveOrUpdate(userToUpdate);
        User retrievedUser = (User)genericDao.getById(3);
        assertEquals(userToUpdate, retrievedUser);
    }
}
