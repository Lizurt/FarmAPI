package org.farm.server.model;

import jakarta.persistence.*;

import java.util.Date;

@Table(name = "product")
@Entity
public class ProductEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private double amount;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date producedDate;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private FarmerEntity producedByFarmer;

    @ManyToOne()
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductTypeEntity productType;
}
