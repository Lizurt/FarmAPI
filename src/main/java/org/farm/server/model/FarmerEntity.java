package org.farm.server.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "farmer")
public class FarmerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farmer_id")
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column
    private String patronymic;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "producedByFarmer")
    private Set<ProductEntity> producedProducts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<ProductEntity> getProducedProducts() {
        return producedProducts;
    }

    public void setProducedProducts(Set<ProductEntity> producedProducts) {
        this.producedProducts = producedProducts;
    }
}
