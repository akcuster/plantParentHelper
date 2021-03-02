package com.acuster.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * A class to represent a user
 *
 * @author acuster
 */
@Entity(name = "User")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

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

    private String userLevel;

    @Column(name = "user_points")
    private int userPoints;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param userName     the user name
     * @param userPassword the user password
     * @param firstName    the first name
     * @param lastName     the last name
     * @param dateOfBirth  the date of birth
     */
    public User(String userName, String userPassword, String firstName, String lastName, LocalDate dateOfBirth) {
        this.userName = userName;
        this.userPassword = userPassword;
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

    /**
     * Gets user level.
     *
     * @return the user level
     */
    public String getUserLevel() {
        return userLevel;
    }

    /**
     * Sets user level.
     *
     * @param userLevel the user level
     */
    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

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
     * Gets user age.
     *
     * @return the age
     */
    public int getAge() {
        return (int) ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && userPoints == user.userPoints &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(userPassword, user.userPassword) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(dateOfBirth, user.dateOfBirth) &&
                Objects.equals(userLevel, user.userLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userPassword, firstName, lastName, dateOfBirth, userLevel, userPoints);
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
                ", userLevel='" + userLevel + '\'' +
                ", userPoints=" + userPoints +
                '}';
    }
}
