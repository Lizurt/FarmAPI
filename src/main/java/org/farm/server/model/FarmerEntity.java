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
}
