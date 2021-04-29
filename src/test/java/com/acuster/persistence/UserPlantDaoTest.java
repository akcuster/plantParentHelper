package com.acuster.persistence;

import com.acuster.entity.Plant;
import com.acuster.entity.User;
import com.acuster.entity.UserPlant;
import com.acuster.test.util.Database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class UserPlantDaoTest {
    GenericDao userPlantDao;
    GenericDao userDao;
    GenericDao plantDao;

    /**
     * Sets up by instantiating PlantDao class.
     */
    @BeforeEach
    void setUp() {

        userPlantDao = new GenericDao(UserPlant.class);
        userDao = new GenericDao(User.class);
        plantDao = new GenericDao(Plant.class);

        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");

    }

    @Test
    void getAllSuccess() {
        List<UserPlant> userPlants = userPlantDao.getAll();
        assertEquals(7, userPlants.size());
    }

    @Test
    void getByIdSuccess() {
        UserPlant userPlant = (UserPlant) userPlantDao.getById(1);
        assert(userPlant.getPlant().getId() == 0);
        assert(userPlant.getUser().getId() == 1);

    }

    @Test
    void deleteUserPlantSuccessfully() {
        List<UserPlant> beforeDelete = userPlantDao.getAll();

        UserPlant userPlant = (UserPlant) userPlantDao.getById(1);
        User user = userPlant.getUser();
        Plant plant = userPlant.getPlant();

        userPlantDao.delete(userPlant);

        List<UserPlant> afterDelete = userPlantDao.getAll();
        // Check that userPlant was deleted
        assertTrue(beforeDelete.contains(userPlant));
        assertFalse(afterDelete.contains(userPlant));
        // Check that the associated user and plant were not deleted
        assertNotNull(user);
        assertNotNull(plant);

    }

}
