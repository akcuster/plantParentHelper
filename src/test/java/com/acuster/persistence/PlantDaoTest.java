package com.acuster.persistence;

import com.acuster.entity.Plant;
import com.acuster.test.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Plant dao test.
 */
class PlantDaoTest {

    GenericDao<Plant> plantDao;

    /**
     * Sets up by instantiating PlantDao class.
     */
    @BeforeEach
    void setUp() {

        plantDao = new GenericDao<>(Plant.class);

        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");

    }

    /**
     * Verifies gets all Plants successfully.
     */
    @Test
    void getAllPlantsSuccess() {
        List<Plant> Plants = plantDao.getAll();
        assertEquals(7, Plants.size());
    }

    /**
     * Verifies a Plant is returned correctly based on id search.
     */
    @Test
    void getPlantsByIdSuccess() {
        Plant retrievedPlant = plantDao.getById(3);
        assertNotNull(retrievedPlant);
        assertEquals("Monstera", retrievedPlant.getPlantName());

    }

    /**
     * Verify successful insert of a Plant
     */
    @Test
    void insertSuccess() {
        Plant newPlant = new Plant("Jade Plant");
        int id = plantDao.insert(newPlant);
        assertNotEquals(0, id);
        Plant insertedPlant = plantDao.getById(id);
        assertEquals(newPlant, insertedPlant);
    }

    /**
     * Verify successful delete of Plant
     */
    @Test
    void deleteSuccess() {
        plantDao.delete(plantDao.getById(3));
        assertNull(plantDao.getById(3));
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyEqualSuccess() {
        List<Plant> Plants = plantDao.getByPropertyEqual("plantName", "Golden Pothos");
        assertEquals(2, Plants.size());
        assertEquals(1, Plants.get(0).getId());
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<Plant> Plants = plantDao.getByPropertyLike("plantName", "p");
        assertEquals(5, Plants.size());
    }

    /**
     * Verify successful update of Plant.
     */
    @Test
    void updateSuccess() {
        String newPlantName = "Maiden Hair Fern";
        Plant PlantToUpdate = plantDao.getById(3);
        PlantToUpdate.setPlantName(newPlantName);
        plantDao.saveOrUpdate(PlantToUpdate);
        Plant retrievedPlant = plantDao.getById(3);
        assertEquals(PlantToUpdate, retrievedPlant);
    }
}

