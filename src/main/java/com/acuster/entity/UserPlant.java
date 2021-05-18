package com.acuster.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

//TODO create a plant profile page linked to from the list of plants on the user's profile
//TODO connect to the task table to keep track of all plant care tasks
/**
 * A class to represent a specific plant in a user's collection.
 */
@Entity(name = "UserPlant")
@Table(name = "user_plant")
public class UserPlant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @Column(name = "date_adopted")
    private LocalDate dateAdopted;

    /**
     * Instantiates a new User plant.
     */
    public UserPlant() {
    }

    /**
     * Instantiates a new User plant.
     *
     * @param user        the user
     * @param plant       the plant
     * @param dateAdopted the date adopted
     */
    public UserPlant(User user, Plant plant, LocalDate dateAdopted) {
        this.user = user;
        this.plant = plant;
        this.dateAdopted = dateAdopted;
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
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets plant.
     *
     * @return the plant
     */
    public Plant getPlant() {
        return plant;
    }

    /**
     * Sets plant.
     *
     * @param plant the plant
     */
    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    /**
     * Gets date adopted.
     *
     * @return the date adopted
     */
    public LocalDate getDateAdopted() {
        return dateAdopted;
    }

    /**
     * Sets date adopted.
     *
     * @param dateAdopted the date adopted
     */
    public void setDateAdopted(LocalDate dateAdopted) {
        this.dateAdopted = dateAdopted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPlant userPlant = (UserPlant) o;
        return id == userPlant.id && Objects.equals(user, userPlant.user) && Objects.equals(plant, userPlant.plant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, plant);
    }

    @Override
    public String toString() {
        return "UserPlant{" +
                "id=" + id +
                ", user=" + user +
                ", plant=" + plant +
                ", dateAdopted=" + dateAdopted +
                '}';
    }
}
