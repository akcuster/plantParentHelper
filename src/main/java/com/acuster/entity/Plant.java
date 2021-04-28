package com.acuster.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "plant_name")
    private String plantName;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserPlant> users = new HashSet<>();

    public Plant() {
    }

    public Plant(String plantName) {
        this.plantName = plantName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public Set<UserPlant> getUsers() {
        return users;
    }

    public void setUsers(Set<UserPlant> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return id == plant.id &&
                Objects.equals(plantName, plant.plantName);
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
