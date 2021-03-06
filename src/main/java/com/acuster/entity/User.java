package com.acuster.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A class to represent a user
 *
 * @author acuster
 */
@Entity(name = "User")
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "user_points")
    private int userPoints;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserPlant> plants = new HashSet<>();

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param userName    the user name
     * @param firstName   the first name
     * @param lastName    the last name
     * @param dateOfBirth the date of birth
     */
    public User(String userName, String firstName, String lastName, LocalDate dateOfBirth) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
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
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets user password.
     *
     * @return the user password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Sets user password.
     *
     * @param userPassword the user password
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets date of birth.
     *
     * @return the date of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets date of birth.
     *
     * @param dateOfBirth the date of birth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    //TODO add points when a plant is added to a user's collection. Eventually also add points when a plant care task is completed
    //TODO add a field for a user's level. Determine level based on points
    /**
     * Gets user points.
     *
     * @return the user points
     */
    public int getUserPoints() {
        return userPoints;
    }

    /**
     * Sets user points.
     *
     * @param userPoints the user points
     */
    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }

    /**
     * Gets plants in a user's collection.
     *
     * @return the plants
     */
    public Set<UserPlant> getPlants() {
        return plants;
    }

    /**
     * Sets plants.
     *
     * @param plants the plants
     */
    public void setPlants(Set<UserPlant> plants) {
        this.plants = plants;
    }

    /**
     * Gets user age.
     *
     * @return the age
     */
    public int getAge() {
        return (int) ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
    }

    /**
     * Add plant to user's collection.
     *
     * @param plant       the plant
     * @param dateAdopted the date adopted
     * @return the user plant
     */
    public UserPlant addPlant(Plant plant, LocalDate dateAdopted) {
        UserPlant userPlant = new UserPlant(this, plant, dateAdopted);
        plants.add(userPlant);
        userPlant.getPlant().getUsers().add(userPlant);

        return userPlant;
    }

    /**
     * Remove plant from user's collection.
     *
     * @param plant the plant
     */
    public void removePlant(UserPlant plant) {

        plant.getPlant().getUsers().remove(plant);
        plants.remove(plant);
        plant.setUser(null);
        plant.setPlant(null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userPoints == user.userPoints && Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && Objects.equals(userPassword, user.userPassword) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(dateOfBirth, user.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userPassword, firstName, lastName, dateOfBirth, userPoints);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + getAge() +
                ", userPoints=" + userPoints +
                '}';
    }
}
