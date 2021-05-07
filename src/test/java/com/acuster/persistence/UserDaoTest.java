package com.acuster.persistence;

import com.acuster.entity.Plant;
import com.acuster.entity.User;
import com.acuster.entity.UserPlant;
import com.acuster.utilities.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type User dao test.
 */
class UserDaoTest {

    GenericDao<User> userDao;
    GenericDao<Plant> plantDao;
    GenericDao<UserPlant> userPlantDao;

    /**
     * Sets up by instantiating UserDao class.
     */
    @BeforeEach
    void setUp() {

        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        userDao = new GenericDao<>(User.class);
        plantDao = new GenericDao<>(Plant.class);
        userPlantDao = new GenericDao<>(UserPlant.class);

    }

    /**
     * Verifies gets all users successfully.
     */
    @Test
    void getAllUsersSuccess() {
        List<User> users = userDao.getAll();
        assertEquals(6, users.size());
    }

    /**
     * Verifies a user is returned correctly base on id search.
     */
    @Test
    void getUsersByIdSuccess() {
        User retrievedUser = userDao.getById(3);
        assertNotNull(retrievedUser);
        assertEquals("Barney", retrievedUser.getFirstName());

    }

    /**
     * Verify successful insert of a user
     */
    @Test
    void insertSuccess() {

        User newUser = new User( "fflintstone", "yabbadabbadoo", "Fred", "Flintstone", LocalDate.parse("1968-01-01"));
        int id = userDao.insert(newUser);
        assertNotEquals(0,id);
        User insertedUser = userDao.getById(id);
        assertEquals(newUser, insertedUser);
    }


    /**
     * Verify successful delete of user
     */
    @Test
    void deleteSuccess() {
        userDao.delete(userDao.getById(3));
        assertNull(userDao.getById(3));
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyEqualSuccess() {
        List<User> users = userDao.getByPropertyEqual("lastName", "Curry");
        assertEquals(1, users.size());
        assertEquals(3, users.get(0).getId());
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<User> users = userDao.getByPropertyLike("lastName", "c");
        assertEquals(3, users.size());
    }

    /**
     * Verify successful update of user.
     */
    @Test
    void updateSuccess() {
        String newLastName = "Davis";
        User userToUpdate = userDao.getById(3);
        userToUpdate.setLastName(newLastName);
        userDao.saveOrUpdate(userToUpdate);
        User retrievedUser = userDao.getById(3);
        assertEquals(userToUpdate, retrievedUser);
    }

    @Test
    void getPlantsSuccess() {
        User user = userDao.getById(1);
        Set<UserPlant> plants = user.getPlants();

        assertEquals(2, plants.size());

    }

    @Test
    void addPlantSuccess() {
        User user = userDao.getById(3);
        List<Plant> plants = plantDao.getByPropertyEqual("plantName", "Monstera");
        Plant newPlant = plants.get(0);
        UserPlant userPlant = user.addPlant(newPlant, LocalDate.parse("2021-04-28"));
        userDao.saveOrUpdate(user);
        List<UserPlant> userPlants = userPlantDao.getAll();

        assertTrue(userPlants.contains(userPlant));
        assertEquals(2, user.getPlants().size());
        assertEquals(1, newPlant.getUsers().size());
    }

    @Test
    void removePlantSuccess() {
        User user = userDao.getById(1);
        UserPlant userPlant = userPlantDao.getById(1);
        Plant plant = userPlant.getPlant();
        user.removePlant(userPlant);
        userDao.saveOrUpdate(user);
        List<UserPlant> userPlants = userPlantDao.getAll();

        assertFalse(userPlants.contains(userPlant));
        assertEquals(1, user.getPlants().size());
        assertEquals(1, plant.getUsers().size());
    }
}
