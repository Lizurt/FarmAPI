package org.farm.server.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "farmer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class FarmerEntity {
    @Id
    @Column(name = "farmer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column
    private String patronymic;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIdentityReference(alwaysAsId = true)
    private UserEntity user;

    @OneToMany(mappedBy = "producedBy")
    @JsonIgnore
    private Set<ProductEntity> producedProducts;

    @OneToMany(mappedBy = "farmer")
    @JsonIgnore
    private Set<RatingEntity> ratings;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Set<ProductEntity> getProducedProducts() {
        return producedProducts;
    }

    public void setProducedProducts(Set<ProductEntity> producedProducts) {
        this.producedProducts = producedProducts;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity asUser) {
        this.user = asUser;
    }
}
