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
}
