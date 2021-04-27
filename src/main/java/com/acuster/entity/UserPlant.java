package com.acuster.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "UserPlant")
@Table(name = "user_plant")
public class UserPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "user_plant_user_id_fk"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @Column(name = "date_adopted")
    private LocalDate dateAdopted;

    public UserPlant() {
    }

    public UserPlant(int id, User user, Plant plant) {
        this.id = id;
        this.user = user;
        this.plant = plant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public LocalDate getDateAdopted() {
        return dateAdopted;
    }

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
