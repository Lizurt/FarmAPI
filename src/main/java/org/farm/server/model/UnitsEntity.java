package org.farm.server.model;

import jakarta.persistence.*;

@Table(name = "units")
@Entity
public class UnitsEntity {
    @Id
    @Column(name = "units_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;
}
