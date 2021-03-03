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
 * The type Plant dao test.
 */
class PlantaDaoTest {

    GenericDao genericDao;

    /**
     * Sets up by instantiating PlantDao class.
     */
    @BeforeEach
    void setUp() {

        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        genericDao = new GenericDao(Plant.class);
    }

    /**
     * Verifies gets all Plants successfully.
     */
    @Test
    void getAllPlantsSuccess() {
        List<Plant> Plants = (List<Plant>) genericDao.getAll();
        assertEquals(6, Plants.size());
    }

    /**
     * Verifies gets Plants by last name successfully.
     */
    @Test
    void getByLastNameSuccess() {
        List<Plant> Plants = (List<Plant>)genericDao.getByLastName("c");
        assertEquals(3, Plants.size());
    }

    /**
     * Verifies a Plant is returned correctly base on id search.
     */
    @Test
    void getPlantsByIdSuccess() {
        Plant retrievedPlant = (Plant)genericDao.getById(3);
        assertNotNull(retrievedPlant);
        assertEquals("Golden Pothos", retrievedPlant.getPlantName());

    }

    /**
     * Verify successful insert of a Plant
     */
    @Test
    void insertSuccess() {
        GenericDao userDao = new GenericDao(User.class);
        User user = (User)userDao.getById(1);
        Plant newPlant = new Plant("Golden Pothos", user);
        int id = genericDao.insert(newPlant);
        assertNotEquals(0, id);
        Plant insertedPlant = (Plant)genericDao.getById(id);
        assertNotNull(insertedPlant.getUser());
        assertEquals(newPlant, insertedPlant);
    }

    /**
     * Verify successful delete of Plant
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
        List<Plant> Plants = (List<Plant>)genericDao.getByPropertyEqual("PlantName", "Golden Pothos");
        assertEquals(1, Plants.size());
        assertEquals(3, Plants.get(0).getId());
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<Plant> Plants = (List<Plant>)genericDao.getByPropertyLike("PlantName", "p");
        assertEquals(3, Plants.size());
    }

    /**
     * Verify successful update of Plant.
     */
    @Test
    void updateSuccess() {
        String newPlantName = "Maiden Hair Fern";
        Plant PlantToUpdate = (Plant)genericDao.getById(3);
        PlantToUpdate.setPlantName(newPlantName);
        genericDao.saveOrUpdate(PlantToUpdate);
        Plant retrievedPlant = (Plant)genericDao.getById(3);
        assertEquals(PlantToUpdate, retrievedPlant);
    }
}

