package com.acuster.persistence;

import com.acuster.entity.Plant;
import com.acuster.entity.User;
import com.acuster.entity.UserPlant;
import com.acuster.test.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void getByIdSuccess() {
        UserPlant userPlant = (UserPlant) userPlantDao.getById(1);
        assert(userPlant.getPlant().getId() == 1);
        assert(userPlant.getUser().getId() == 1);

    }
}
