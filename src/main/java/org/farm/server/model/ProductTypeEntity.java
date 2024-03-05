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
}
