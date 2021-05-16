package com.acuster.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A class to represent a plant.
 *
 * @author acuster
 */
@Entity(name = "Plant")
@Table(name = "plant")
public class Plant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
//    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;

    @Column(name = "plant_name")
    private String plantName;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserPlant> users = new HashSet<>();

    /**
     * Instantiates a new Plant.
     */
    public Plant() {
    }

    /**
     * Instantiates a new Plant.
     *
     * @param plantName the plant name
     */
    public Plant(String plantName) {
        this.plantName = plantName;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets plant name.
     *
     * @return the plant name
     */
    public String getPlantName() {
        return plantName;
    }

    /**
     * Sets plant name.
     *
     * @param plantName the plant name
     */
    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    /**
     * Gets users.
     *
     * @return the users
     */
    public Set<UserPlant> getUsers() {
        return users;
    }

    /**
     * Sets users.
     *
     * @param users the users
     */
    public void setUsers(Set<UserPlant> users) {
        this.users = users;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return Objects.equals(id, plant.id) && Objects.equals(plantName, plant.plantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plantName);
    }

    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", plantName='" + plantName + '\'' +
                '}';
    }
}
