package org.farm.server.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Table(name = "units")
@Entity
public class UnitsEntity {
    @Id
    @Column(name = "units_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "units")
    @JsonIgnore
    private Set<ProductTypeEntity> products;

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

    public Set<ProductTypeEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductTypeEntity> products) {
        this.products = products;
    }
}
