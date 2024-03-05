package org.farm.server.model;

import jakarta.persistence.*;

import java.util.Set;

@Table(name = "product_type")
@Entity
public class ProductTypeEntity {
    @Id
    @Column(name = "product_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "units_id", nullable = false)
    private UnitsEntity units;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productType")
    private Set<ProductEntity> products;

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

    public UnitsEntity getUnits() {
        return units;
    }

    public void setUnits(UnitsEntity units) {
        this.units = units;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }
}
